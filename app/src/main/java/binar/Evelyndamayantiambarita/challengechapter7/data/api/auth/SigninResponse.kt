package binar.Evelyndamayantiambarita.challengechapter7.data.api.auth

import com.google.gson.annotations.SerializedName

data class SigninResponse(
    @SerializedName("user-token") var userToken: String? = null,
    @SerializedName("objectId") var objectId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("job") var job: String? = null,
    @SerializedName("image") var image: String? = null
)