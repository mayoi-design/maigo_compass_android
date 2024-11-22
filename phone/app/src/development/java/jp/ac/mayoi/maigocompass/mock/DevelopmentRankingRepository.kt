package jp.ac.mayoi.maigocompass.mock

import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.phone.model.RemoteRankingArea
import jp.ac.mayoi.phone.model.RemoteSpot
import jp.ac.mayoi.repository.interfaces.RankingRepository
import kotlinx.coroutines.delay

class DevelopmentRankingRepository : RankingRepository {
    private var availAreaGetCount = 0
    private var rankingGetCount = 0

    override suspend fun getAvailArea(): List<RemoteRankingArea> {
        delay(2000)

        if (availAreaGetCount % 3 == 2) {
            throw Exception("Development Error")
        }

        availAreaGetCount += 1
        return List(6) {
            RemoteRankingArea(
                areaId = "$it",
                areaName = "エリア名 ${it + 1}",
            )
        }
    }

    override suspend fun getRanking(areaId: String): List<LocalSpot> {
        val idrem = areaId.toIntOrNull() ?: 0
        delay(2000)

        if (availAreaGetCount % 3 == 2) {
            throw Exception("Development Error")
        }

        rankingGetCount += 1
        val remoteSpots = when (idrem % 3) {
            0 -> {
                List(15) {
                    RemoteSpot(
                        lat = 0.0f,
                        lng = 0.0f,
                        message = "ランキングスポット ${it + 1}",
                        imageUrl = "",
                        createdAt = "2024-11-03T00:00:00+09:00",
                        reachedCount = 10 * it,
                        spotId = "",
                    )
                }
            }
            1 -> {
                List(3) {
                    RemoteSpot(
                        lat = 0.0f,
                        lng = 0.0f,
                        message = "ランキングスポット ${it + 1}",
                        imageUrl = "",
                        createdAt = "2024-11-03T00:00:00+09:00",
                        reachedCount = 10 * it,
                        spotId = "",
                    )
                }
            }
            2 -> {
                listOf()
            }
            else -> {
                listOf() // Unreachable
            }
        }

        return remoteSpots.map { spot ->
            LocalSpot(
                lat = spot.lat,
                lng = spot.lng,
                message = spot.message,
                imageUrl = spot.imageUrl,
                spotId = spot.spotId,
                reachedCount = spot.reachedCount,
                createdAt = spot.createdAt,
            )
        }
    }
}
