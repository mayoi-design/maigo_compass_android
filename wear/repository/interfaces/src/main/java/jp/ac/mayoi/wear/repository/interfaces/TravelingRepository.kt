package jp.ac.mayoi.wear.repository.interfaces

import com.google.android.gms.wearable.MessageClient
import jp.ac.mayoi.common.model.RemoteSpotShrink
import jp.ac.mayoi.common.model.RemoteSpotShrinkList
import kotlinx.coroutines.flow.StateFlow

abstract class TravelingRepository : MessageClient.OnMessageReceivedListener {
    abstract val recommendSpot: StateFlow<RemoteSpotShrinkList>
}