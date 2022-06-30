package binar.Evelyndamayantiambarita.challengechapter7.data.local

import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM favorite")
    suspend fun getFavorite(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite WHERE favoriteId like :favoriteId")
    suspend fun searchFavoriteId(favoriteId: Int): List<FavoriteEntity>

    @Query("DELETE FROM favorite WHERE favoriteId = :favoriteId")
    suspend fun removeFromFavorite(favoriteId: Int)

    @Query("DELETE FROM favorite")
    suspend fun deleteFavorite()
}