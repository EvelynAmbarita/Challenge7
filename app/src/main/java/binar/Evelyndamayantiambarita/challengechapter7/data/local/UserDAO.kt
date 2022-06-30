package binar.Evelyndamayantiambarita.challengechapter7.data.local

import androidx.room.*

@Dao
interface UserDAO {
    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Query("UPDATE user SET name = :name, job = :job WHERE id = :id")
    suspend fun updateUser(id: String, name: String, job: String)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity): Int
}