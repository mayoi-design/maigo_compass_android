package jp.ac.mayoi.wear.repository.implementations

import jp.ac.mayoi.common.model.RemoteSpotShrink
import jp.ac.mayoi.wear.repository.interfaces.TravelingRepository

class TravelingRepositoryImpl : TravelingRepository {
    override suspend fun getRecommendSpots(): List<RemoteSpotShrink> {
        TODO("Not yet implemented")
    }
}