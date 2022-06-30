package binar.Evelyndamayantiambarita.challengechapter7.data.api.auth

import com.google.gson.annotations.SerializedName

data class SignupRequest(
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("job") var job: String? = null,
    @SerializedName("password") var password: String? = null
)
