package binar.Evelyndamayantiambarita.challengechapter7.ui.menu.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.Evelyndamayantiambarita.challengechapter7.Constant
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.MoviesPopularResponse
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.MoviesUpcomingResponse
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.TMDBAPI
import binar.Evelyndamayantiambarita.challengechapter7.model.ProfileModel
import binar.Evelyndamayantiambarita.challengechapter7.repository.AuthRepository
import binar.Evelyndamayantiambarita.challengechapter7.repository.MovieRepository
import binar.Evelyndamayantiambarita.challengechapter7.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    val shouldShowMoviePopular: MutableLiveData<MoviesPopularResponse> = MutableLiveData()
    val shouldShowMovieUpcoming: MutableLiveData<MoviesUpcomingResponse> = MutableLiveData()
    val shouldShowProfile: MutableLiveData<ProfileModel> = MutableLiveData()

    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    fun onViewLoaded() {
        getProfile()
        getMoviePopular()
        getMovieUpcoming()
    }

    private fun getProfile() {
        viewModelScope.launch {
            val result = profileRepository.getProfile()
            withContext(Dispatchers.Main) {
                result.let {
                    shouldShowProfile.postValue(it)
                }
            }
        }
    }

    private fun getMoviePopular(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = movieRepository.getMoviePopular()
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowMoviePopular.postValue(result.body())
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }

    private fun getMovieUpcoming(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = movieRepository.getMovieUpcoming()
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowMovieUpcoming.postValue(result.body())
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
        }
    }

}