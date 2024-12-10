package jp.ac.mayoi.wear.repository.implementations

import android.util.Log
import com.google.android.gms.wearable.MessageClient
import jp.ac.mayoi.common.model.RemoteSpotShrinkList
import jp.ac.mayoi.common.resource.dataLayerRecommendSpotPath
import jp.ac.mayoi.wear.repository.interfaces.TravelingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

class TravelingRepositoryImpl(
    private val messageClient: MessageClient,
    initialValue: RemoteSpotShrinkList = RemoteSpotShrinkList(listOf())
) : TravelingRepository() {
    private val _recommendSpot: MutableStateFlow<RemoteSpotShrinkList> =
        MutableStateFlow(initialValue)
    override val recommendSpot: StateFlow<RemoteSpotShrinkList> = _recommendSpot.asStateFlow()

    override fun startReceiveSpots() {
        messageClient.addListener(messageClientListener)
    }

    override fun stopReceiveSpots() {
        messageClient.removeListener(messageClientListener)
    }

    private val messageClientListener = MessageClient.OnMessageReceivedListener { p0 ->
        when (p0.path) {
            dataLayerRecommendSpotPath -> {
                val data = p0.data.toString(Charsets.UTF_8)
                try {
                    val spotList: RemoteSpotShrinkList = Json.decodeFromString(data)
                    Log.d("TravelingRepository", "$spotList")
                    runBlocking {
                        _recommendSpot.emit(
                            RemoteSpotShrinkList(
                                spotList.spots
                            )
                        )
                    }
                } catch (e: Exception) {
                    Log.d("TravelingRepository", "Failed to parse recommend spots message.\n$data")
                }
            }
        }
    }
}
