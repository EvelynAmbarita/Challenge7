package binar.Evelyndamayantiambarita.challengechapter7.repository

import binar.Evelyndamayantiambarita.challengechapter7.data.api.auth.*
import binar.Evelyndamayantiambarita.challengechapter7.data.local.UserDAO
import binar.Evelyndamayantiambarita.challengechapter7.data.local.UserEntity
import binar.Evelyndamayantiambarita.challengechapter7.datastore.AuthDataSoreManager
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDatastore: AuthDataSoreManager,
    private val api: AuthAPI,
    private val dao: UserDAO
) {
    suspend fun clearToken() {
        updateToken("")
    }

    suspend fun updateToken(value: String) {
        authDatastore.setToken(value)
    }

    suspend fun getToken(): String? {
        return authDatastore.getToken().firstOrNull()
    }

    suspend fun signIn(request: SigninRequest): Response<SigninResponse> {
        return api.signIn(request)
    }

    suspend fun signUp(request: SignupRequest): Response<SignupResponse> {
        return api.signUp(request)
    }

    suspend fun logout(): Response<Unit> {
        val headers = mapOf("user-token" to getToken().orEmpty())
        return api.logout(headers)
    }

    suspend fun insertUser(userEntity: UserEntity): Long {
        return dao.insertUser(userEntity)
    }

    suspend fun updateUser(id: String, name: String, job: String) {
        return dao.updateUser(id = id, name = name, job = job)
    }

}