package binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBAPI {

    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Query("api_key") apiKey: String
    ): Response<MoviesPopularResponse>

    @GET("movie/upcoming")
    suspend fun getMovieUpcoming(
        @Query("api_key") apiKey: String
    ): Response<MoviesUpcomingResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDetailsResponse>
}