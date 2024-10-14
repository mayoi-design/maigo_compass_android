package jp.ac.mayoi.service.interfaces

import retrofit2.Response
import retrofit2.http.GET

interface HealthService {
    @GET("health")
    suspend fun health(): Response<Unit>
}