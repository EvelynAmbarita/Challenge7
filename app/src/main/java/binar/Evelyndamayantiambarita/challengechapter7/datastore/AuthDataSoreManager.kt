package binar.Evelyndamayantiambarita.challengechapter7.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import binar.Evelyndamayantiambarita.challengechapter7.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthDataSoreManager @Inject constructor(private val context: Context) {
    companion object {
        val Context.dataStoreAuth: DataStore<Preferences> by preferencesDataStore(
            name = Constant.PrefDataSore.PREF_NAME,
            produceMigrations = ::sharedPreferencesMigration
        )

        private fun sharedPreferencesMigration(context: Context) =
            listOf(SharedPreferencesMigration(context, Constant.PrefDataSore.PREF_NAME))
    }

    fun getToken(): Flow<String> {
        return context.dataStoreAuth.data.map { preferences ->
            preferences[Constant.PrefDataSore.TOKEN_KEY].orEmpty()
        }
    }

    suspend fun setToken(value: String) {
        context.dataStoreAuth.edit { preferences ->
            preferences[Constant.PrefDataSore.TOKEN_KEY] = value
        }
    }
}