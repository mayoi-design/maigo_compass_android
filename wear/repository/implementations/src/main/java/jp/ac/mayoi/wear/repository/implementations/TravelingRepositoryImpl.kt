package jp.ac.mayoi.wear.repository.implementations

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import jp.ac.mayoi.common.model.RemoteSpotShrinkList
import jp.ac.mayoi.wear.repository.interfaces.TravelingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class TravelingRepositoryImpl(
    initialValue: RemoteSpotShrinkList = RemoteSpotShrinkList(listOf())
) : TravelingRepository() {

    private val _recommendSpot: MutableStateFlow<RemoteSpotShrinkList> = MutableStateFlow(initialValue)
    override val recommendSpot: StateFlow<RemoteSpotShrinkList> = _recommendSpot

    override fun onMessageReceived(messageEvent: MessageEvent) {
        when (messageEvent.path) {
            LOCATION_DATA -> {
                Log.d("Message", "message Changed")
                //jsonをここでデシリアライズする
                //spotにいれる
                val deserialized = Json.decodeFromString<RemoteSpotShrinkList>(messageEvent.data.toString(Charsets.UTF_8))

                CoroutineScope(Dispatchers.IO).launch {
                    _recommendSpot.emit(deserialized)
                }

            }
        }
        Log.d("Message", "message receive")
    }

    companion object {
        const val LOCATION_DATA = "/location-data"
    }
}
