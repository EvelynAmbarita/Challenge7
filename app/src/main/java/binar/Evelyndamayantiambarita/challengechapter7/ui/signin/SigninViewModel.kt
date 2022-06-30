package binar.Evelyndamayantiambarita.challengechapter7.ui.signin

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.Evelyndamayantiambarita.challengechapter7.data.api.ErrorResponse
import binar.Evelyndamayantiambarita.challengechapter7.data.api.auth.SigninRequest
import binar.Evelyndamayantiambarita.challengechapter7.data.local.UserEntity
import binar.Evelyndamayantiambarita.challengechapter7.repository.AuthRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private var email: String = ""
    private var password: String = ""

    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldOpenMenuPage: MutableLiveData<Boolean> = MutableLiveData()

    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun onViewLoaded() {}

    fun onChangeEmail(email: String) {
        this.email = email
    }

    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onClickSignIn() {
        if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            shouldShowError.postValue("Email tidak valid")
        } else if (password.isNotEmpty() && password.length < 8) {
            shouldShowError.postValue("Password tidak valid")
        } else {
            signInFromAPI()
        }
    }

    private fun signInFromAPI() {
        shouldShowLoading.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            val request = SigninRequest(
                login = email,
                password = password
            )
            val response = authRepository.signIn(request)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val signinResponse = response.body()
                    signinResponse?.let {
                        insertToken(it.userToken.orEmpty())

                        val userEntity = UserEntity(
                            id = it.objectId.orEmpty(),
                            name = it.name.orEmpty(),
                            email = it.email.orEmpty(),
                            job = it.job.orEmpty(),
                            image = it.image.orEmpty()
                        )
                        insertProfile(userEntity)
                    }
                } else {
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
            }
            shouldShowLoading.postValue(false)
        }
    }

    private fun insertToken(token: String) {
        if (token.isNotEmpty()) {
            viewModelScope.launch {
                authRepository.updateToken(token)
            }
        }
    }

    private fun insertProfile(userEntity: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = authRepository.insertUser(userEntity)
            withContext(Dispatchers.Main) {
                if (result != 0L) {
                    shouldOpenMenuPage.postValue(true)
                } else {
                    shouldShowError.postValue("Maaf, gagal insert ke dalam database")
                }
            }
        }
    }

}