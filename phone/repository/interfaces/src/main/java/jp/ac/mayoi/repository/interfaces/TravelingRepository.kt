package jp.ac.mayoi.repository.interfaces

import jp.ac.mayoi.phone.model.RemoteSpot

interface TravelingRepository {
    suspend fun getNearSpots(
        lat: Double,
        lng: Double,
    ): List<RemoteSpot>
}