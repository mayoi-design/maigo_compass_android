package jp.ac.mayoi.traveling

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import jp.ac.mayoi.common.model.RemoteSpotShrinkList
import jp.ac.mayoi.common.resource.locationIntentAction
import jp.ac.mayoi.common.resource.locationIntentLatitude
import jp.ac.mayoi.common.resource.locationIntentLongitude
import jp.ac.mayoi.common.resource.locationPolingInterval
import jp.ac.mayoi.core.util.LoadState
import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.repository.interfaces.TravelingRepository
import jp.ac.mayoi.wear.service.LocationService
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.coroutines.cancellation.CancellationException

class TravelingViewModel(
    private val travelingRepository: TravelingRepository,
    private val messageClient: MessageClient,
    private val capabilityClient: CapabilityClient,
) : ViewModel() {
    var spotListState: LoadState<ImmutableList<LocalSpot>> by mutableStateOf(
        LoadState.Loading(null)
    )
        private set

    var previousState: LoadState<ImmutableList<LocalSpot>> = spotListState
        private set

    private var currentLocation: Location = Location(null).also {
        it.latitude = 0.0
        it.longitude = 0.0
    }

    fun getNearSpot() {
        val currentLat = currentLocation.latitude
        val currentLng = currentLocation.longitude
        previousState = spotListState
        spotListState = LoadState.Loading(spotListState.value)
        viewModelScope.launch {
            try {
                val spots =
                    travelingRepository.getNearSpots(currentLat, currentLng).toImmutableList()
                spotListState = LoadState.Success(spots)
                sendLocalSpot(spots)
            } catch (exception: Exception) {
                Log.e("getNearSpot", "${exception.message}")
                spotListState = LoadState.Error(spotListState.value, exception)
            }
        }
    }

    private fun sendLocalSpot(
        localSpot: ImmutableList<LocalSpot>,
    ) {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        val payload = RemoteSpotShrinkList(
            spots = localSpot.map { it.toRemoteSpotShrink() },
        )
        val jsonString = Json.encodeToString(payload)

        scope.launch {
            try {
                Log.d("Message", "Message send start")
                val nodes = capabilityClient
                    .getCapability("wear", CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes
                Log.d("Message", nodes.toString())
                Log.d("Message", jsonString)
                nodes.map { node ->
                    async {
                        Log.d("Message", node.id)
                        messageClient.sendMessage(
                            node.id,
                            "/location-data",
                            jsonString.toByteArray(Charsets.UTF_8)
                        )
                            .await()
                    }
                }.awaitAll()

                Log.d("Message", "send end")
            } catch (cancellationException: CancellationException) {
                Log.d("Message", "Message cancel")
                //throw cancellationException
            } catch (exception: Exception) {
                Log.d("Message", "Message failed")
            }
        }
    }

    fun startLocationUpdate(
        context: Context,
    ) {
        val intentFilter = IntentFilter().also {
            it.addAction(locationIntentAction)
        }
        val intent = Intent(
            context.applicationContext,
            LocationService::class.java
        ).also {
            it.putExtra(locationPolingInterval, 10000L)
        }

        Log.d("LocationViewModel", "Starting LocationService")
        context.applicationContext.startService(intent)
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

    fun stopLocationUpdate(
        context: Context
    ) {
        Log.d("LocationViewModel", "Stopping LocationService")
        context.applicationContext.stopService(
            Intent(context.applicationContext, LocationService::class.java)
        )
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val bundle = intent.extras ?: return
            currentLocation.latitude =
                bundle.getDouble(locationIntentLatitude, 0.0)
            currentLocation.longitude =
                bundle.getDouble(locationIntentLongitude, 0.0)

            val maybeInitialLoad =
                spotListState is LoadState.Loading && spotListState.value == null
            if (maybeInitialLoad || spotListState is LoadState.Success) {
                getNearSpot()
            }
        }
    }
}
