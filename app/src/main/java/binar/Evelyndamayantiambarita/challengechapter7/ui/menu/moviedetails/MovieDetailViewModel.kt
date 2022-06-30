package binar.Evelyndamayantiambarita.challengechapter7.ui.menu.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.Evelyndamayantiambarita.challengechapter7.Constant
import binar.Evelyndamayantiambarita.challengechapter7.data.local.FavoriteEntity
import binar.Evelyndamayantiambarita.challengechapter7.model.MovieDetailModel
import binar.Evelyndamayantiambarita.challengechapter7.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    @Named(Constant.Named.BASE_URL_TMDB_IMAGE) private val imageUrl: String
): ViewModel() {

    val shouldShowMovieDetail: MutableLiveData<MovieDetailModel> = MutableLiveData()
    val shouldFavorited: MutableLiveData<Boolean> = MutableLiveData()

    val shouldShowFavoriteButton: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowUnfavoriteButton: MutableLiveData<Boolean> = MutableLiveData()

    fun getMovieDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getMovieDetail(id)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    val movieDetailsResponse = result.body()
                    val movieDetailsModel = movieDetailsResponse?.genres?.map {
                        MovieDetailModel.Genres(
                            id = it.id.hashCode(),
                            title = it.name.orEmpty()
                        )
                    }?.let {
                        MovieDetailModel(
                            id = movieDetailsResponse.id.hashCode(),
                            title = movieDetailsResponse.title.orEmpty(),
                            poster_path = imageUrl + movieDetailsResponse.posterPath.orEmpty(),
                            backdrop_path = imageUrl + movieDetailsResponse.backdropPath.orEmpty(),
                            release_date = movieDetailsResponse.releaseDate.orEmpty(),
                            genres = it,
                            overview = movieDetailsResponse.overview.orEmpty()
                        )
                    }
                    shouldShowMovieDetail.postValue(movieDetailsModel!!)
                }
            }
        }
    }

    fun addToFavorite(movieId: Int, title: String, poster_path: String) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModelScope.launch {
                repository.addToFavorite(movieId, title, poster_path)
                shouldShowUnfavoriteButton.postValue(true)
                shouldShowFavoriteButton.postValue(false)
            }
        }
    }

    fun searchFavoriteId(favoriteId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.searchFavoriteId(favoriteId)
            withContext(Dispatchers.Main) {
                if (result.isNotEmpty()) {
                    shouldFavorited.postValue(true)
                }
            }
        }
    }

    fun removeFromFavorite(movieId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModelScope.launch {
                repository.removeFromFavorite(movieId)
                shouldShowFavoriteButton.postValue(true)
                shouldShowUnfavoriteButton.postValue(false)
            }
        }
    }
}