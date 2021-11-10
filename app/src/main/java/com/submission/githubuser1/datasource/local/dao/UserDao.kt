package com.submission.githubuser1.datasource.local.dao

import androidx.room.*
import com.submission.githubuser1.model.User

/****************************************************
 * Created by Indra Muliana
 * On Wednesday, 03/11/2021 19.41
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE `id` = :key")
    suspend fun find(key: String): User?

    @Query("SELECT * FROM users ORDER BY id DESC")
    suspend fun all(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: List<User>)

    @Update
    suspend fun update(value: User)

    @Delete
    suspend fun delete(recipe: User)

    @Query("DELETE FROM users")
    suspend fun delete()

    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE `id` = :key)")
    suspend fun exist(key: String): Boolean

}