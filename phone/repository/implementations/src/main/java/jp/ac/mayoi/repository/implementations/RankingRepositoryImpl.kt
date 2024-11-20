package jp.ac.mayoi.repository.implementations

import jp.ac.mayoi.phone.model.RemoteRankingArea
import jp.ac.mayoi.phone.model.RemoteSpot
import jp.ac.mayoi.repository.interfaces.RankingRepository
import jp.ac.mayoi.service.interfaces.RankingService

class RankingRepositoryImpl(
    private val rankingService: RankingService,
) : RankingRepository {

    override suspend fun getAvailArea(): List<RemoteRankingArea> {
        return rankingService.getRankingArea()
    }

    override suspend fun getRanking(areaId: String): List<RemoteSpot> {
        return rankingService.getRanking(areaId)
    }
}