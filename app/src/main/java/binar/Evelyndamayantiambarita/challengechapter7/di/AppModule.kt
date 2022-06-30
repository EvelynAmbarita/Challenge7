package binar.Evelyndamayantiambarita.challengechapter7.di

import binar.Evelyndamayantiambarita.challengechapter7.Constant
import binar.Evelyndamayantiambarita.challengechapter7.data.api.auth.AuthAPI
import binar.Evelyndamayantiambarita.challengechapter7.data.api.imageupload.ImageUploadAPI
import binar.Evelyndamayantiambarita.challengechapter7.data.local.UserDAO
import binar.Evelyndamayantiambarita.challengechapter7.datastore.AuthDataSoreManager
import binar.Evelyndamayantiambarita.challengechapter7.repository.AuthRepository
import binar.Evelyndamayantiambarita.challengechapter7.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
        authDataStoreManager: AuthDataSoreManager,
        api: AuthAPI,
        dao: UserDAO
    ): AuthRepository {
        return AuthRepository(
            authDatastore = authDataStoreManager,
            api = api,
            dao = dao
        )
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        imageUploadAPI: ImageUploadAPI,
        authAPI: AuthAPI,
        userDAO: UserDAO,
        @Named(Constant.Named.APIKEY_IMAGE_UPLOAD) apiKey: String
    ): ProfileRepository {
        return ProfileRepository(
            imageUploadAPI = imageUploadAPI,
            authAPI = authAPI,
            dao = userDAO,
            apiKeyImageUpload = apiKey
        )
    }

}