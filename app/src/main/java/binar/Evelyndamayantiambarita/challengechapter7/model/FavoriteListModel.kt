package binar.Evelyndamayantiambarita.challengechapter7.model

import com.google.gson.annotations.SerializedName

data class FavoriteListModel(
    @SerializedName("result") val result: List<FavoriteListResult>
) {
    data class FavoriteListResult (
        @SerializedName("id") val id: Int? = null,
        @SerializedName("title") val title: String? = null,
        @SerializedName("poster_path") val poster_path: String? = null
    )
}