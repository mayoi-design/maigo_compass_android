package jp.ac.mayoi.wear.repository.interfaces

import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.WearableListenerService
import jp.ac.mayoi.common.model.RemoteSpotShrink
import jp.ac.mayoi.common.model.RemoteSpotShrinkList
import kotlinx.coroutines.flow.StateFlow

abstract class TravelingRepository : WearableListenerService()  {
    abstract val recommendSpot: StateFlow<RemoteSpotShrinkList>
}