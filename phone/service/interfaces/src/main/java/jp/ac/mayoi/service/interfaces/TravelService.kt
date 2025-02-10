package jp.ac.mayoi.service.interfaces

import jp.ac.mayoi.phone.model.RemoteRankingArea
import retrofit2.http.GET
import retrofit2.http.Query

interface TravelService {

    @GET("travel/area")
    suspend fun getAreaByCoordinate(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
    ): RemoteRankingArea
}