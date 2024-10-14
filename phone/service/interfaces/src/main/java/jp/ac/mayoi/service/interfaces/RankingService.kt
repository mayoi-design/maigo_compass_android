package jp.ac.mayoi.service.interfaces

import jp.ac.mayoi.phone.model.RankingAreaResponse
import retrofit2.http.GET

interface RankingService {
    @GET("get_ranking_area")
    suspend fun get_ranking_area(): RankingAreaResponse


}