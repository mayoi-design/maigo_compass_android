package jp.ac.mayoi.service.interfaces

import jp.ac.mayoi.phone.model.AdapterResisterReach
import jp.ac.mayoi.phone.model.RemoteSpotList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotService {
    @GET("get_spot")
    suspend fun get_spot(
        @Query("lat") lat: Float,
        @Query("lng") lng: Float,
    ): RemoteSpotList

    @GET("get_my_spot/{user_id}")
    suspend fun get_my_spot(
        @Path("user_id") userId: String,
    ): RemoteSpotList

    @Headers("Content-Type: application/json")
    @POST("resister_reach")
    suspend fun resister_reach(
        @Body body: AdapterResisterReach
    ): Call<Unit>
}