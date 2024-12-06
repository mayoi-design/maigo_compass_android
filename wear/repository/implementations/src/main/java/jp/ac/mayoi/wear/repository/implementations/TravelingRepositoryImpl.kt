package jp.ac.mayoi.wear.repository.implementations

import android.content.Intent
import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
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

    override fun onCreate() {
        super.onCreate()
        Log.d("TravelingRepository", "Created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("TravelingRepository", "Started")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        Log.d("Message", "message receive")
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
    }

    companion object {
        const val LOCATION_DATA = "/location-data"
    }
}
