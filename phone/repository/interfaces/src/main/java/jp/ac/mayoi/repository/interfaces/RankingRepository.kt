package jp.ac.mayoi.repository.interfaces

import jp.ac.mayoi.phone.model.LocalSpot
import jp.ac.mayoi.phone.model.RemoteRankingArea

interface RankingRepository {
    suspend fun getAvailArea(): List<RemoteRankingArea>
    suspend fun getRanking(areaId: String): List<LocalSpot>
}