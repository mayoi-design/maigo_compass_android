package jp.ac.mayoi.service.interfaces

import jp.ac.mayoi.phone.model.RemoteRanking
import jp.ac.mayoi.phone.model.RemoteRankingAreaList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RankingService {
    @GET("get_ranking_area")
    suspend fun get_ranking_area(): RemoteRankingAreaList

    @GET("get_ranking/{area_id}")
    suspend fun get_ranking(
        @Path("area_id") areaId: Int,
    ): Call<RemoteRanking>
}