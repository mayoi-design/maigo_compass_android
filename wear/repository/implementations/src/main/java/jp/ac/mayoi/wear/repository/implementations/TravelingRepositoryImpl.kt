package jp.ac.mayoi.wear.repository.implementations

import jp.ac.mayoi.common.model.RemoteSpotShrinkList
import jp.ac.mayoi.wear.repository.interfaces.TravelingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TravelingRepositoryImpl(
    initialValue: RemoteSpotShrinkList = RemoteSpotShrinkList(listOf())
) : TravelingRepository() {
    private val _recommendSpot: MutableStateFlow<RemoteSpotShrinkList> = MutableStateFlow(initialValue)
    override val recommendSpot: StateFlow<RemoteSpotShrinkList> = _recommendSpot

    companion object {
        const val LOCATION_DATA = "/location-data"
    }
}
