package jp.ac.mayoi.wear.features.waiting

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.wearable.MessageClient
import jp.ac.mayoi.common.model.Destination
import jp.ac.mayoi.wear.core.resource.dataLayerDestinationPath
import kotlinx.serialization.json.Json

class WaitingScreenViewModel(
    private val messageClient: MessageClient,
    private val onDestinationReceivedListener: (Destination) -> Unit,
) : ViewModel() {
    var isButtonView: Boolean by mutableStateOf(true)
        private set

    fun onSetDestinationButtonClick() {
        isButtonView = false
    }

    fun startReceivingMessage() {
        messageClient.addListener(messageClientListener)
    }

    fun stopReceivingMessage() {
        messageClient.removeListener(messageClientListener)
    }

    private val messageClientListener = MessageClient.OnMessageReceivedListener { p0 ->
        when (p0.path) {
            dataLayerDestinationPath -> {
                val data = p0.data.toString(Charsets.UTF_8)
                try {
                    val destination: Destination = Json.decodeFromString(data)
                    Log.d("TravelingRepository", "Destination data received: $destination")
                    onDestinationReceivedListener(destination)
                } catch (e: Exception) {
                    Log.d("TravelingRepository", "Failed to parse destination from message.\n$data")
                }
            }

            else -> Unit
        }
    }
}
