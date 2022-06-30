package binar.Evelyndamayantiambarita.challengechapter7.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import binar.Evelyndamayantiambarita.challengechapter7.data.local.FavoriteDAO
import binar.Evelyndamayantiambarita.challengechapter7.data.local.FavoriteEntity
import binar.Evelyndamayantiambarita.challengechapter7.data.local.UserDAO
import binar.Evelyndamayantiambarita.challengechapter7.data.local.UserEntity

@Database(entities = [UserEntity::class, FavoriteEntity::class], version = 4)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun favoriteDAO(): FavoriteDAO

    companion object {
        private const val DB_NAME = "MyDoctor.db"

        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): LocalDatabase {
            return Room.databaseBuilder(context, LocalDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}