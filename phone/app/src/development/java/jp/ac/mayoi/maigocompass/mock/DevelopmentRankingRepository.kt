package jp.ac.mayoi.maigocompass.mock

import jp.ac.mayoi.phone.model.RemoteRankingArea
import jp.ac.mayoi.phone.model.RemoteSpot
import jp.ac.mayoi.repository.interfaces.RankingRepository

class DevelopmentRankingRepository : RankingRepository {
    override suspend fun getAvailArea(): List<RemoteRankingArea> {
        TODO("Not yet implemented")
    }

    override suspend fun getRanking(areaId: String): List<RemoteSpot> {
        TODO("Not yet implemented")
    }
}