package jp.ac.mayoi.maigocompass.mock

import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.repository.interfaces.TravelingRepository
import kotlinx.coroutines.delay

class DevelopmentTravelingRepository : TravelingRepository {
    private var nearSpotsGetCount = 0

    override suspend fun getNearSpots(lat: Double, lng: Double): List<LocalSpot> {
        delay(2000)

        if (nearSpotsGetCount % 3 == 2) {
            throw Exception("Development Error")
        }

        nearSpotsGetCount += 1

        return List(10) {
            LocalSpot(
                lat = 0.0f,
                lng = 0.0f,
                message = "近くのスポット ${it + 1}",
                imageUrl = "",
                createdAt = "2024-11-03T00:00:00+09:00",
                reachedCount = 10 * it,
                spotId = "",
            )
        }
    }
}