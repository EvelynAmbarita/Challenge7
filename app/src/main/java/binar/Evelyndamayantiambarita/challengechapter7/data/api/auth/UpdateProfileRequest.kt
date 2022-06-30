package binar.Evelyndamayantiambarita.challengechapter7.data.api.auth

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("name") var name: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("job") var job: String? = null
)
