package binar.Evelyndamayantiambarita.challengechapter7.di

import binar.Evelyndamayantiambarita.challengechapter7.Constant
import binar.Evelyndamayantiambarita.challengechapter7.data.api.auth.AuthAPI
import binar.Evelyndamayantiambarita.challengechapter7.data.api.imageupload.ImageUploadAPI
import binar.Evelyndamayantiambarita.challengechapter7.data.api.tmdb.TMDBAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    @Named(Constant.Named.BASE_URL_MYDOCTOR)
    fun provideBaseUrlMyDoctor(): String = "https://happyplace.backendless.app/api/"

    @Singleton
    @Provides
    @Named(Constant.Named.BASE_URL_IMAGE_UPLOAD)
    fun provideBaseUrlImageUpload(): String = "https://api.imgbb.com/1/"

    @Singleton
    @Provides
    @Named(Constant.Named.APIKEY_IMAGE_UPLOAD)
    fun provideApiKeyImageUpload(): String = "56e074fb9f11e246bde93fecb8ba5204"

    @Singleton
    @Provides
    @Named(Constant.Named.BASE_URL_TMDB)
    fun provideBaseUrlTMDB(): String = "https://api.themoviedb.org/3/"

    @Singleton
    @Provides
    @Named(Constant.Named.BASE_URL_TMDB_IMAGE)
    fun provideBaseUrlImage(): String = "https://image.tmdb.org/t/p/w500/"


    @Singleton
    @Provides
    @Named(Constant.Named.APIKEY_TMDB)
    fun provideApiKey(): String = "0fbaf8c27d542bc99bfc67fb877e3906"

    @Singleton
    @Provides
    fun provideHttpLogging(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    @Named(Constant.Named.RETROFIT_MYDOCTOR)
    fun provideRetrofitMyDoctor(
        @Named(Constant.Named.BASE_URL_MYDOCTOR) baseUrl: String,
        client: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    @Named(Constant.Named.RETROFIT_IMAGE_UPLOAD)
    fun provideRetrofitImage(
        @Named(Constant.Named.BASE_URL_IMAGE_UPLOAD) baseUrl: String,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    @Named(Constant.Named.RETROFIT_TMDB)
    fun provideRetrofitTMDB(
        @Named(Constant.Named.BASE_URL_TMDB) baseUrl: String,
        client: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthAPI(
        @Named(Constant.Named.RETROFIT_MYDOCTOR) retrofit: Retrofit
    ): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideImageUploadAPI(
        @Named(Constant.Named.RETROFIT_IMAGE_UPLOAD) retrofit: Retrofit
    ): ImageUploadAPI {
        return retrofit.create(ImageUploadAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideTMDBAPI(
        @Named(Constant.Named.RETROFIT_TMDB) retrofit: Retrofit
    ) : TMDBAPI {
        return retrofit.create(TMDBAPI::class.java)
    }

}