package jp.ac.mayoi.repository.interfaces

import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.phone.model.enums.ServiceAreaAdapter

interface TravelingRepository {
    suspend fun getNearSpots(
        lat: Double,
        lng: Double,
    ): List<LocalSpot>

    suspend fun getAreaByCoordinate(
        lat: Double,
        lng: Double,
    ): ServiceAreaAdapter.ServiceArea
}