package jp.ac.mayoi.maigocompass.mock

import jp.ac.mayoi.phone.model.RemoteSpot
import jp.ac.mayoi.repository.interfaces.TravelingRepository

class DevelopmentTravelingRepository : TravelingRepository {
    override suspend fun getNearSpots(lat: Double, lng: Double): List<RemoteSpot> {
        TODO("Not yet implemented")
    }
}