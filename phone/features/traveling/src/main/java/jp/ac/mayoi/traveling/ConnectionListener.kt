package jp.ac.mayoi.traveling

import android.util.Log
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent

class ConnectionListener() : MessageClient.OnMessageReceivedListener {

    override fun onMessageReceived(messageEvent: MessageEvent) {
        when(messageEvent.path){
            "/destination-path" -> {
                Log.d("Message", messageEvent.data.toString())
                //この実装方針がわからない　どうやってnavigateを変える？？
            }

        }
    }


}
