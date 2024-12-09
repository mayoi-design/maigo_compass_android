package jp.ac.mayoi.wear.repository.interfaces

import jp.ac.mayoi.common.model.RemoteSpotShrinkList
import kotlinx.coroutines.flow.StateFlow

abstract class TravelingRepository {
    abstract val recommendSpot: StateFlow<RemoteSpotShrinkList>
}