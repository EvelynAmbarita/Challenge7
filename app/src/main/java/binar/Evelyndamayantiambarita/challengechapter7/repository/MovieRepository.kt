package binar.Evelyndamayantiambarita.challengechapter7.repository

import androidx.lifecycle.MutableLiveData
import binar.Evelyndamayantiambarita.challengechapter7.Constant
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.MovieDetailsResponse
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.MoviesPopularResponse
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.MoviesUpcomingResponse
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.TMDBAPI
import binar.Evelyndamayantiambarita.challengechapter7.data.local.FavoriteDAO
import binar.Evelyndamayantiambarita.challengechapter7.data.local.FavoriteEntity
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class MovieRepository @Inject constructor(
    private val TMDBAPI: TMDBAPI,
    private val dao: FavoriteDAO,
    @Named(Constant.Named.APIKEY_TMDB) private val apiKey: String
) {
    suspend fun getMoviePopular(): Response<MoviesPopularResponse> {
        return TMDBAPI.getMoviePopular(apiKey)
    }

    suspend fun getMovieUpcoming(): Response<MoviesUpcomingResponse> {
        return TMDBAPI.getMovieUpcoming(apiKey)
    }

    suspend fun getMovieDetail(id: Int): Response<MovieDetailsResponse> {
        return TMDBAPI.getMovieDetails(id = id, apiKey)
    }

    suspend fun getFavorite(): List<FavoriteEntity> = dao.getFavorite()

    suspend fun addToFavorite(movieId: Int, title: String, poster_path: String) {
        val addToFavorite = FavoriteEntity(
            favoriteId = movieId,
            title = title,
            poster_path = poster_path
        )
        return dao.insertFavorite(addToFavorite)
    }

    suspend fun searchFavoriteId(favoriteId: Int): List<FavoriteEntity> = dao.searchFavoriteId(favoriteId)

    suspend fun removeFromFavorite(id: Int) {
        return dao.removeFromFavorite(favoriteId = id)
    }

    suspend fun deleteFavorite() {
        dao.deleteFavorite()
    }
}