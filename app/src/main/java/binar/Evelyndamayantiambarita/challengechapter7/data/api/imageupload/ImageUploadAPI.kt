package binar.Evelyndamayantiambarita.challengechapter7.data.api.imageupload

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ImageUploadAPI {
    @POST("upload")
    @Multipart
    suspend fun uploadImage(
        @Query("expiration") expiration: Int = 10000,
        @Query("key") key: String,
        @Part image: MultipartBody.Part
    ): Response<ImageDataResponse>
}