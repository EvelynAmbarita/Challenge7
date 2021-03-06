package binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb

import com.google.gson.annotations.SerializedName

data class MoviesUpcomingResponse(
    @SerializedName("page"          ) val page          : Int?               = null,
    @SerializedName("results"       ) val results       : List<ResultMoviesUpcoming> = emptyList(),
    @SerializedName("total_pages"   ) val total_pages   : Int?               = null,
    @SerializedName("total_results" ) val total_results : Int?               = null
) {
    data class ResultMoviesUpcoming(
        @SerializedName("adult"             ) val adult             : Boolean?       = null,
        @SerializedName("backdrop_path"     ) val backdrop_path     : String?        = null,
        @SerializedName("genre_ids"         ) val genre_ids         : List<Int> = emptyList(),
        @SerializedName("id"                ) val id                : Int?           = null,
        @SerializedName("original_language" ) val original_language : String?        = null,
        @SerializedName("original_title"    ) val original_title    : String?        = null,
        @SerializedName("overview"          ) val overview          : String?        = null,
        @SerializedName("popularity"        ) val popularity        : Double?        = null,
        @SerializedName("poster_path"       ) val poster_path       : String?        = null,
        @SerializedName("release_date"      ) val release_date      : String?        = null,
        @SerializedName("title"             ) val title             : String?        = null,
        @SerializedName("video"             ) val video             : Boolean?       = null,
//        @SerializedName("vote_average"      ) val vote_average      : Int?           = null,
//        @SerializedName("vote_count"        ) val vote_count        : Int?           = null
    )
}