package jp.ac.mayoi.service.interfaces

import jp.ac.mayoi.phone.model.RemoteImage
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageService {
    @Multipart
    @POST("upload_image")
    suspend fun upload_image(
        @Part("image") image: RequestBody,
        @Part("user_id") userId: RequestBody,
    ): RemoteImage
}