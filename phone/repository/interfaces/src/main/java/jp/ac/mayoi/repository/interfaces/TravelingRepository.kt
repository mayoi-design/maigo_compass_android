package jp.ac.mayoi.repository.interfaces

import jp.ac.mayoi.phone.model.LocalSpot

interface TravelingRepository {
    suspend fun getNearSpots(
        lat: Double,
        lng: Double,
    ): List<LocalSpot>
}