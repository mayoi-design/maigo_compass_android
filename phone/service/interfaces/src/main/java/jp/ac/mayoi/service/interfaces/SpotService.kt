package jp.ac.mayoi.service.interfaces

import jp.ac.mayoi.phone.model.AdapterResisterReach
import jp.ac.mayoi.phone.model.RemoteSpot
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotService {
    @POST("spot")
    suspend fun registerNewSpot(
        @Body adapter: AdapterResisterReach,
    ): Response<RemoteSpot>

    @GET("spots/by_cood")
    suspend fun getSpotByLatLng(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
    ): List<RemoteSpot>

    @GET("spots/by_user/{user_id}")
    suspend fun getSpotByUser(
        @Path("user_id") userId: String,
    ): List<RemoteSpot>

    @POST("spot/{spot_id}")
    suspend fun registerReach(
        @Path("spot_id") spotId: String,
    ): Call<Unit>
}