package jp.ac.mayoi.traveling

import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.ac.mayoi.common.resource.locationIntentAction
import jp.ac.mayoi.common.resource.locationIntentLatitude
import jp.ac.mayoi.common.resource.locationIntentLongitude
import jp.ac.mayoi.common.resource.locationPolingInterval
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import jp.ac.mayoi.common.model.RemoteSpotShrink
import jp.ac.mayoi.common.model.RemoteSpotShrinkList
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
        // todo: repositoryから取得できる現在地のlat, lngをつかって、spotListStateを更新する
        val currentLat = currentLocation.latitude
        val currentLng = currentLocation.longitude
        spotListState = LoadState.Loading(spotListState.value)
        viewModelScope.launch {
            try {
                val spots =
                    travelingRepository.getNearSpots(currentLat, currentLng)
                spotListState = LoadState.Success(spots.toImmutableList())
            } catch (exception: Exception) {
                Log.e("getNearSpot", "${exception.message}")
                spotListState = LoadState.Error(spotListState.value, exception)
            }
        }
    }

    fun sendLocalSpot(
        dataPath: String,
        localSpot: ImmutableList<LocalSpot>,
        messageClient: MessageClient,
        capabilityClient: CapabilityClient
    ) {

        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

        val payload = localSpotConvertString(localSpot)

        scope.launch {
            try {
                Log.d("Message", "Message send start")
                val nodes = capabilityClient
                    .getCapability("wear", CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes
                Log.d("Message", nodes.toString())
                Log.d("Message", payload)
                nodes.map { node ->
                    async {
                        Log.d("Message", node.id)
                        messageClient.sendMessage(
                            node.id,
                            dataPath,
                            payload.toByteArray(Charsets.UTF_8)
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

    private fun localSpotConvertString(localSpot: ImmutableList<LocalSpot>): String {
        val newList = mutableListOf<RemoteSpotShrink>()
        for (item in localSpot) {
            val tmp = RemoteSpotShrink(item.lat.toDouble(), item.lng.toDouble(), item.message)
            newList.add(tmp)
        }
        val sendString = Json.encodeToString(RemoteSpotShrinkList(newList))
        return sendString
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
