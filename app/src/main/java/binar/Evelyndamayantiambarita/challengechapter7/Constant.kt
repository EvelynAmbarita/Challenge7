package binar.Evelyndamayantiambarita.challengechapter7

import androidx.datastore.preferences.core.stringPreferencesKey

object Constant {

    object PrefDataSore {
        const val PREF_NAME = "MyDoctorLogin"
        val TOKEN_KEY = stringPreferencesKey("TOKEN")
    }

    object ImageUrl {
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    }

    object Named {
        const val BASE_URL_MYDOCTOR = "BASE_URL_MYDOCTOR"
        const val BASE_URL_IMAGE_UPLOAD = "BASE_URL_IMAGE_UPLOAD"
        const val BASE_URL_TMDB = "BASE_URL_TMDB"
        const val BASE_URL_TMDB_IMAGE = "BASE_URL_TMDB_IMAGE"

        const val APIKEY_TMDB = "APIKEY_TMDB"
        const val APIKEY_IMAGE_UPLOAD = "APIKEY_IMAGE_UPLOAD"

        const val RETROFIT_MYDOCTOR = "RETROFIT_MYDOCTOR"
        const val RETROFIT_IMAGE_UPLOAD = "RETROFIT_IMAGE_UPLOAD"
        const val RETROFIT_TMDB = "RETROFIT_TMDB"
    }
}