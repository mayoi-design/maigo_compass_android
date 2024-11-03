package jp.ac.mayoi.maigocompass.mock

import jp.ac.mayoi.phone.model.MemoryAreaModel
import jp.ac.mayoi.phone.model.UserMemory
import jp.ac.mayoi.repository.interfaces.MemoryRepository
import kotlinx.coroutines.delay

class DevelopmentMemoryRepository : MemoryRepository {
    override suspend fun getUserMemory(): UserMemory {
        // この関数はExceptionを吐かない
        delay(1000)

        return UserMemory(
            totalTripCount = 30,
            spotShareCount = 18,
            areaShareCounts = listOf(
                MemoryAreaModel(
                    areaName = "エリア 1",
                    areaCount = 3,
                    areaId = "",
                ),
                MemoryAreaModel(
                    areaName = "エリア 2",
                    areaCount = 4,
                    areaId = "",
                ),
                MemoryAreaModel(
                    areaName = "エリア 3",
                    areaCount = 5,
                    areaId = "",
                ),
                MemoryAreaModel(
                    areaName = "エリア 4",
                    areaCount = 6,
                    areaId = "",
                ),
            ),
            exploreSpotCount = 10,
            firstTripDate = "2024-04-15T00:00:00+09:00",
            latestTripDate = "2024-11-03T00:00:00+09:00"
        )
    }
}