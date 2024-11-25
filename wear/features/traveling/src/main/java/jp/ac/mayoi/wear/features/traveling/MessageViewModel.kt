package jp.ac.mayoi.wear.features.traveling

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import jp.ac.mayoi.common.model.RemoteSpotShrink
import jp.ac.mayoi.common.model.RemoteSpotShrinkList
import kotlinx.serialization.json.Json

//確実にViewModelではないことはないわかっております
class MessageViewModel:
    ViewModel(),
    MessageClient.OnMessageReceivedListener {

    var spots = mutableStateListOf<RemoteSpotShrink>()
        private set


    var test = mutableStateOf("")


    override fun onMessageReceived(messageEvent: MessageEvent) {
        when(messageEvent.path){
            "/location-data" -> {
                Log.d("Message", "message Changed")
                //jsonをここでデシリアライズする
                //spotにいれる
                val deserialized = Json.decodeFromString<RemoteSpotShrinkList>(messageEvent.data.toString(Charsets.UTF_8))
                spots.clear()
                spots.addAll(deserialized.spots)
                Log.d("Message",test.value)
            }

        }
        Log.d("Message","message receive")
    }

    fun getSpotList():List<RemoteSpotShrink>{
        return spots.toList()
    }

}