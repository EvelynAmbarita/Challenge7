package binar.Evelyndamayantiambarita.challengechapter7.data.api.auth

import retrofit2.Response
import retrofit2.http.*

interface AuthAPI {
    @POST("users/login")
    suspend fun signIn(@Body request: SigninRequest): Response<SigninResponse>

    @POST("users/register")
    suspend fun signUp(@Body request: SignupRequest): Response<SignupResponse>

    @GET("users/logout")
    suspend fun logout(@HeaderMap headers: Map<String, String>): Response<Unit>

    @PUT("users/{ID}")
    suspend fun updateProfile(
        @Path("ID") id: String,
        @Body request: UpdateProfileRequest
    ): Response<SigninResponse>
}