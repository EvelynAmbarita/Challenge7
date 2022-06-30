package binar.Evelyndamayantiambarita.challengechapter7.ui.menu.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.Evelyndamayantiambarita.challengechapter7.data.local.FavoriteEntity
import binar.Evelyndamayantiambarita.challengechapter7.model.FavoriteListModel
import binar.Evelyndamayantiambarita.challengechapter7.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: MovieRepository
) : ViewModel() {

    val shouldShowMoviesFavorite: MutableLiveData<List<FavoriteEntity>> = MutableLiveData()

    fun onViewLoaded() {
        getMovieFavorite()
    }

    private fun getMovieFavorite(){
        viewModelScope.launch {
            val result = favoriteRepository.getFavorite()
            withContext(Dispatchers.Main) {
                result.let {
                    shouldShowMoviesFavorite.postValue(it)
                }
            }
        }
    }
}