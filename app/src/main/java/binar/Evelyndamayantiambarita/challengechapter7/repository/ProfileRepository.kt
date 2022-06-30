package binar.Evelyndamayantiambarita.challengechapter7.repository

import binar.Evelyndamayantiambarita.challengechapter7.Constant
import binar.Evelyndamayantiambarita.challengechapter7.common.Resource
import binar.Evelyndamayantiambarita.challengechapter7.common.Status
import binar.Evelyndamayantiambarita.challengechapter7.data.api.auth.*
import binar.Evelyndamayantiambarita.challengechapter7.data.api.imageupload.ImageDataResponse
import binar.Evelyndamayantiambarita.challengechapter7.data.api.imageupload.ImageUploadAPI
import binar.Evelyndamayantiambarita.challengechapter7.data.local.UserDAO
import binar.Evelyndamayantiambarita.challengechapter7.data.local.UserEntity
import binar.Evelyndamayantiambarita.challengechapter7.model.ProfileModel
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Named

class ProfileRepository @Inject constructor(
    private val imageUploadAPI: ImageUploadAPI,
    private val authAPI: AuthAPI,
    private val dao: UserDAO,
    @Named(Constant.Named.APIKEY_IMAGE_UPLOAD) private val apiKeyImageUpload: String
) {
    suspend fun getProfile(): ProfileModel {
        return dao.getUser().let {
            ProfileModel(
                id = it?.id.orEmpty(),
                name = it?.name.orEmpty(),
                email = it?.email.orEmpty(),
                job = it?.job.orEmpty(),
                image = it?.image.orEmpty()
            )
        }
    }

    suspend fun uploadImage(image: MultipartBody.Part): Resource<ImageDataResponse> {
        imageUploadAPI.uploadImage(
            image = image,
            key = apiKeyImageUpload
        ). let {
            if (it.isSuccessful) {
                updateImageProfile(image = it.body()?.data?.thumb?.url.orEmpty())
                return Resource(
                    status = Status.SUCCESS,
                    data = it.body(),
                    message = null
                )
            } else {
                return Resource(
                    status = Status.ERROR,
                    data = null,
                    message = it.errorBody().toString()
                )
            }
        }
    }

    private suspend fun updateImageProfile(image: String): Long {
        val profile = dao.getUser()
        val updateProfile = UserEntity(
            id = profile?.id.orEmpty(),
            email = profile?.email.orEmpty(),
            name = profile?.name.orEmpty(),
            job = profile?.job.orEmpty(),
            image = image,
        )
        return dao.insertUser(updateProfile)
    }

    suspend fun updateProfile(updateProfile: UpdateProfileRequest, image: String): Resource<SigninResponse> {
        val profile = getProfile()
        val request = UpdateProfileRequest(
            name = updateProfile.name,
            image = image,
            job = updateProfile.job
        )
        return authAPI.updateProfile(
            id = profile.id,
            request = request
        ).let {
            if (it.isSuccessful) {
                Resource(
                    status = Status.SUCCESS,
                    data = it.body(),
                    message = null
                )
            } else {
                Resource(
                    status = Status.ERROR,
                    data = null,
                    message = it.errorBody().toString()
                )
            }
        }
    }

    suspend fun deleteProfile(): Int {
        val profile = dao.getUser()
        val deleteProfile = UserEntity(
            id = profile?.id.orEmpty(),
            email = profile?.email.orEmpty(),
            name = profile?.name.orEmpty(),
            job = profile?.job.orEmpty(),
            image = profile?.image.orEmpty()
        )
        return dao.deleteUser(deleteProfile)
    }
}