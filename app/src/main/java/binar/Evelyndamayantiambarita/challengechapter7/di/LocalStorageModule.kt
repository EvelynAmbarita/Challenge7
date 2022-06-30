package binar.Evelyndamayantiambarita.challengechapter7.di

import android.content.Context
import binar.Evelyndamayantiambarita.challengechapter7.data.local.FavoriteDAO
import binar.Evelyndamayantiambarita.challengechapter7.data.local.UserDAO
import binar.Evelyndamayantiambarita.challengechapter7.database.LocalDatabase
import binar.Evelyndamayantiambarita.challengechapter7.datastore.AuthDataSoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): LocalDatabase {
        return LocalDatabase.getInstance(context = context)
    }

    @Singleton
    @Provides
    fun provideUserDao(db: LocalDatabase): UserDAO {
        return db.userDAO()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(db: LocalDatabase): FavoriteDAO {
        return db.favoriteDAO()
    }

    @Singleton
    @Provides
    fun provideAuthDataStoreManager(@ApplicationContext context: Context)
            : AuthDataSoreManager {
        return AuthDataSoreManager(context = context)
    }
}