package binar.Evelyndamayantiambarita.challengechapter7.data.api.auth

import com.google.gson.annotations.SerializedName

data class SigninRequest(
    @SerializedName("login") var login: String? = null,
    @SerializedName("password") var password: String? = null
)