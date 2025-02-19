package jp.ac.mayoi.service.interfaces

import jp.ac.mayoi.phone.model.RemoteRankingArea
import jp.ac.mayoi.phone.model.RemoteSpot
import retrofit2.http.GET
import retrofit2.http.Query

interface RankingService {
    @GET("spot/ranking/area")
    suspend fun getRankingArea(): List<RemoteRankingArea>

    @GET("spot/ranking")
    suspend fun getRanking(
        @Query("area") areaId: String,
    ): List<RemoteSpot>
}