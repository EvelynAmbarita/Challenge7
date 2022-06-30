package binar.Evelyndamayantiambarita.challengechapter7.ui.signup

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import binar.Evelyndamayantiambarita.challengechapter7.data.api.ErrorResponse
import binar.Evelyndamayantiambarita.challengechapter7.data.api.auth.SigninRequest
import binar.Evelyndamayantiambarita.challengechapter7.data.api.auth.SignupRequest
import binar.Evelyndamayantiambarita.challengechapter7.data.local.UserEntity
import binar.Evelyndamayantiambarita.challengechapter7.database.LocalDatabase
import binar.Evelyndamayantiambarita.challengechapter7.repository.AuthRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private var nickname: String = ""
    private var job: String = ""
    private var email: String = ""
    private var password: String = ""
    private var retypePassword: String = ""

    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenUpdateProfile: MutableLiveData<Boolean> = MutableLiveData()

    fun onChangeNickname(nickname: String) {
        this.nickname = nickname
    }

    fun onChangeJob(job: String) {
        this.job = job
    }

    fun onChangeEmail(email: String) {
        this.email = email
    }

    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onChangeRetypePassword(retypePassword: String) {
        this.retypePassword = retypePassword
    }

    fun onValidate() {
        if (nickname.isEmpty() && nickname.length < 3) {
            shouldShowError.postValue("Nickname tidak valid")
        } else if (email.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            shouldShowError.postValue("Email tidak valid")
        } else if (password.isEmpty() && password.length < 8) {
            shouldShowError.postValue("Password tidak valid")
        } else if (retypePassword != password) {
            shouldShowError.postValue("Password tidak sama")
        } else {
            processToSignUp()
        }
    }

    private fun processToSignUp() {
        CoroutineScope(Dispatchers.IO).launch {
            shouldShowLoading.postValue(true)
            val request = SignupRequest(
                name = nickname,
                email = email,
                job = job,
                password = password
            )
            val result = authRepository.signUp(request = request)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    processToSignIn()
                } else {
                    showErrorMessage(result.errorBody())
                    shouldShowLoading.postValue(false)
                }
            }
        }
    }

    private fun processToSignIn() {
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
                }
            }
        }
    }

    private fun showErrorMessage(response: ResponseBody?) {
        val error = Gson().fromJson(response?.string(), ErrorResponse::class.java)
        shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
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
                    shouldOpenUpdateProfile.postValue(true)
                } else {
                    shouldShowError.postValue("Maaf, gagal insert ke dalam database")
                }
            }
        }
    }
}