package jp.ac.mayoi.wear.features.traveling

import android.annotation.SuppressLint
import android.app.Activity.RECEIVER_EXPORTED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.ac.mayoi.common.model.RemoteSpotShrink
import jp.ac.mayoi.wear.core.resource.locationIntentAction
import jp.ac.mayoi.wear.core.resource.locationIntentLatitude
import jp.ac.mayoi.wear.core.resource.locationIntentLongitude
import jp.ac.mayoi.wear.model.RecommendSpot
import jp.ac.mayoi.wear.repository.interfaces.CompassRepository
import jp.ac.mayoi.wear.repository.interfaces.LocationRepository
import jp.ac.mayoi.wear.repository.interfaces.TravelingRepository
import jp.ac.mayoi.wear.service.LocationService
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

class TravelingViewModel(
    private val sensorManager: SensorManager,
    private val compassRepository: CompassRepository,
    private val locationRepository: LocationRepository, // あとで使うのでとりあえず
    private val travelingRepository: TravelingRepository,
    private val _destination: Location,
) : ViewModel() {
    var headingTo by mutableDoubleStateOf(0.0)
        private set
    private val currentLocation = run {
        Location(null).also {
            it.latitude = 0.0
            it.longitude = 0.0
        }
    }
    private var remoteRecommendSpot: List<RemoteSpotShrink> = listOf()

    // recommendSpotとdestinationの実装終わってるからどうにかしたい
    // recompositionのたびに a*(len(remoteRecommendSpot) + 1) 回計算が走ることになる
    val recommendSpot: ImmutableList<RecommendSpot>
        get() = remoteRecommendSpot.map { spot ->
            RecommendSpot(
                spot,
                currentLocation.distanceTo(spot.location).toDouble(),
                locationRepository.getBearing(currentLocation, spot.location, headingTo)
            )
        }.toImmutableList()
    val destination: RecommendSpot
        get() = RecommendSpot(
            lat = _destination.latitude,
            lng = _destination.longitude,
            comment = "",
            distance = currentLocation.distanceTo(_destination).toDouble(),
            bearing = locationRepository.getBearing(currentLocation, _destination, headingTo)
        )
    var focusing: RecommendSpot by mutableStateOf(destination)

    private val rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    init {
        viewModelScope.launch {
            travelingRepository.recommendSpot.collect {
                remoteRecommendSpot = it.spots
                Log.d("TravelingViewModel", "updating remoteRecommendSpot: $it")
            }
        }
    }

    fun startSensor(context: Context) {
        sensorManager.registerListener(
            sensorCallbackListener,
            rotationVectorSensor,
            SensorManager.SENSOR_DELAY_UI
        )
        registerLocationBroadcastReceiver(context)
    }

    fun stopSensor(context: Context) {
        sensorManager.unregisterListener(
            sensorCallbackListener,
            rotationVectorSensor
        )
        context.applicationContext.stopService(
            Intent(context.applicationContext, LocationService::class.java)
        )
    }

    private fun registerLocationBroadcastReceiver(
        context: Context
    ) {
        val intentFilter = IntentFilter().also {
            it.addAction(locationIntentAction)
        }

        context.applicationContext.startService(
            Intent(context.applicationContext, LocationService::class.java)
        )
        @SuppressLint("UnspecifiedRegisterReceiverFlag")
        if (Build.VERSION.SDK_INT < 33) {
            context.registerReceiver(
                broadcastReceiver,
                intentFilter
            )
        } else {
            context.registerReceiver(
                broadcastReceiver,
                intentFilter,
                RECEIVER_EXPORTED
            )
        }
    }

    private val sensorCallbackListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            if (event == null) return

            when (event.sensor.type) {
                Sensor.TYPE_ROTATION_VECTOR -> {
                    headingTo = compassRepository.getCurrentAzimuth(
                        event.values.dropLast(1).toFloatArray()
                    )
                }
            }
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            // todo: 精度が低い時に「キャリブレーションしてください」みたいな警告をUIに出すようにしたい。
        }
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras ?: return
            currentLocation.latitude = bundle.getDouble(locationIntentLatitude, 0.0)
            currentLocation.longitude = bundle.getDouble(locationIntentLongitude, 0.0)
            Log.d(
                "Traveling",
                "Current pos: (${currentLocation.latitude}, ${currentLocation.longitude})"
            )
        }
    }
}