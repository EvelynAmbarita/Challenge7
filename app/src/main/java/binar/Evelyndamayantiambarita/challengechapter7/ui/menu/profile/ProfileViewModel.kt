package binar.Evelyndamayantiambarita.challengechapter7.ui.menu.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.Evelyndamayantiambarita.challengechapter7.common.Status
import binar.Evelyndamayantiambarita.challengechapter7.data.api.ErrorResponse
import binar.Evelyndamayantiambarita.challengechapter7.data.api.auth.UpdateProfileRequest
import binar.Evelyndamayantiambarita.challengechapter7.data.local.UserDAO
import binar.Evelyndamayantiambarita.challengechapter7.model.ProfileModel
import binar.Evelyndamayantiambarita.challengechapter7.repository.AuthRepository
import binar.Evelyndamayantiambarita.challengechapter7.repository.MovieRepository
import binar.Evelyndamayantiambarita.challengechapter7.repository.ProfileRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dao: UserDAO,
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {

    val shouldShowProfile: MutableLiveData<ProfileModel> = MutableLiveData()
    val shouldShowImageProfile: MutableLiveData<String> = MutableLiveData()

    val shouldShowSuccess: MutableLiveData<String> = MutableLiveData()
    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

    val shouldShowLogin: MutableLiveData<Boolean> = MutableLiveData()

    fun onViewLoaded() {
        getProfile()
    }

    private fun getProfile() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = profileRepository.getProfile()
            withContext(Dispatchers.Main) {
                result.let {
                    shouldShowProfile.postValue(it)
                }
            }
        }
    }

    fun uploadImage(body: MultipartBody.Part) {
        shouldShowLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val result = profileRepository.uploadImage(body)
            withContext(Dispatchers.Main) {
                when (result.status) {
                    Status.SUCCESS -> {
                        shouldShowImageProfile.postValue(result.data?.data?.thumb?.url)
                        updateProfile(UpdateProfileRequest(),
                            image = result.data?.data?.thumb?.url.orEmpty())
                    }
                    Status.ERROR -> {
                        shouldShowError.postValue(result.message.orEmpty())
                        shouldShowLoading.postValue(false)
                    }
                    Status.LOADING -> {

                    }
                }
            }
            getProfile()
        }
    }

    fun updateProfile(profile: UpdateProfileRequest, image: String?){
        shouldShowLoading.postValue(true)
        viewModelScope.launch {
            val result = profileRepository.updateProfile(updateProfile = profile, image.toString())
            withContext(Dispatchers.Main) {
                when (result.status) {
                    Status.SUCCESS -> {
                    }
                    Status.ERROR -> {
                        shouldShowError.postValue(result.message.orEmpty())
                    }
                    Status.LOADING -> {
                    }
                }
                shouldShowLoading.postValue(false)
            }
        }
    }

    fun updateDatabase(name: String, job: String, id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = authRepository.updateUser(name = name, job = job, id = id)
            withContext(Dispatchers.Main) {
                result.let {
                    shouldShowLoading.postValue(false)
                }
            }
        }
    }

    fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            shouldShowLoading.postValue(true)
            val result = authRepository.logout()
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldShowLoading.postValue(false)
                    clearToken()
                } else {
                    showErrorMessage(response = result.errorBody())
                    shouldShowLoading.postValue(false)
                }
            }
        }
    }

    private fun clearToken() {
        viewModelScope.launch {
            authRepository.clearToken()
            profileRepository.deleteProfile()
            movieRepository.deleteFavorite()
            withContext(Dispatchers.Main) {
                shouldShowLogin.postValue(true)
            }
        }
    }

    private fun showErrorMessage(response: ResponseBody? = null) {
        val error = Gson().fromJson(response?.string() ?: "", ErrorResponse::class.java)
        shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
    }

}