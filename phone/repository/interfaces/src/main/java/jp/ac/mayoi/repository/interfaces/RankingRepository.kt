package jp.ac.mayoi.repository.interfaces

import jp.ac.mayoi.phone.model.RemoteRankingArea
import jp.ac.mayoi.phone.model.RemoteSpot

interface RankingRepository {
    suspend fun getAvailArea(): List<RemoteRankingArea>
    suspend fun getRanking(areaId: String): List<RemoteSpot>
}