package jp.ac.mayoi.maigocompass.mock

import jp.ac.mayoi.phone.model.RemoteSpot
import jp.ac.mayoi.repository.interfaces.TravelingRepository
import kotlinx.coroutines.delay

class DevelopmentTravelingRepository : TravelingRepository {
    override suspend fun getNearSpots(lat: Double, lng: Double): List<RemoteSpot> {
        delay(2000)
        return List(10) {
            RemoteSpot(
                lat = 0.0f,
                lng = 0.0f,
                message = "近くのスポット ${it + 1}",
                imageUrl = "",
                postUserId = "",
                createdAt = "2024-11-03T00:00:00+09:00",
                reachedCount = 10 * it,
                spotId = "",
            )
        }
    }
}