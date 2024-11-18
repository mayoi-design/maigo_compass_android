package jp.ac.mayoi.wear.repository.interfaces

import jp.ac.mayoi.common.model.RemoteSpotShrink

interface TravelingRepository {
    suspend fun getRecommendSpots(): List<RemoteSpotShrink>
}