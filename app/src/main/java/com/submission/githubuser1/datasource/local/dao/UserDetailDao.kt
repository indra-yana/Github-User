package com.submission.githubuser1.datasource.local.dao

import androidx.room.*
import com.submission.githubuser1.model.UserDetail

/****************************************************
 * Created by Indra Muliana
 * On Wednesday, 03/11/2021 19.45
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

@Dao
interface UserDetailDao {

    @Query("SELECT * FROM user_details WHERE `id` = :key")
    suspend fun find(key: Int): UserDetail?

    @Query("SELECT * FROM user_details WHERE `login` = :username")
    suspend fun find(username: String): UserDetail?

    @Query("SELECT * FROM user_details")
    suspend fun all(): List<UserDetail>

    @Query("SELECT * FROM user_details WHERE is_favourite = 1")
    suspend fun getUserFavourite(): List<UserDetail>

    @Query("SELECT * FROM user_details WHERE `id` LIKE '%' || :key || '%' AND `name` LIKE '%' || :key || '%' AND is_favourite = 1")
    suspend fun findUserFavourite(key: String): List<UserDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: UserDetail)

    @Delete
    suspend fun delete(value: UserDetail)

    @Query("SELECT EXISTS (SELECT 1 FROM user_details WHERE `id` = :key)")
    suspend fun exist(key: String): Boolean

    @Query("UPDATE user_details SET `is_favourite` = :isFavourite WHERE `id` = :key")
    suspend fun setFavourite(key: Int, isFavourite: Boolean): Int

    @Query("SELECT is_favourite FROM user_details WHERE `id` = :key")
    suspend fun isFavourite(key: Int): Int

}