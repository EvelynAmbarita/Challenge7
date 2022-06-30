package binar.Evelyndamayantiambarita.challengechapter7.ui.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.Evelyndamayantiambarita.challengechapter7.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    val shouldOpenSignIn: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenMenuPage: MutableLiveData<Boolean> = MutableLiveData()

    fun onViewLoaded() {
        viewModelScope.launch {
            val result = repository.getToken()
            withContext(Dispatchers.Main) {
                if (result.isNullOrEmpty()) {
                    shouldOpenSignIn.postValue(true)
                } else {
                    shouldOpenMenuPage.postValue(true)
                }
            }
        }
    }
}