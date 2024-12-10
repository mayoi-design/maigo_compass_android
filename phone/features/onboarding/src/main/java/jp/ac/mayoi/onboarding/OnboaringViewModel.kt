package jp.ac.mayoi.onboarding

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import jp.ac.mayoi.common.model.Destination
import jp.ac.mayoi.core.resource.dataLayerDestinationPath
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class OnboardingViewModel(
    private val messageClient: MessageClient,
    private val capabilityClient: CapabilityClient,
) : ViewModel() {
    private var destination: LatLng by mutableStateOf(LatLng(0.0, 0.0))

    fun onCameraChanged(newPosition: LatLng) {
        destination = newPosition
    }

    fun onBeforeNavigateToTravelingScreen(): Deferred<Result<Unit>> {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        val payload = Destination(
            lat = destination.latitude,
            lng = destination.longitude,
        )
        val jsonString = Json.encodeToString(payload)

        return scope.async {
            try {
                val nodes = capabilityClient
                    .getCapability("wear", CapabilityClient.FILTER_REACHABLE)
                    .await()
                    .nodes
                nodes.map { node ->
                    async {
                        messageClient.sendMessage(
                            node.id,
                            dataLayerDestinationPath,
                            jsonString.toByteArray(Charsets.UTF_8)
                        ).await()
                    }
                }.awaitAll()
                Result.success(Unit)
            } catch (e: CancellationException) {
                Log.d("OnboardingViewModel", "Canceled")
                Result.failure(e)
            } catch (e: Exception) {
                Log.d("OnboardingViewModel", "Failed to send message")
                Result.failure(e)
            }
        }
    }
}