package com.submission.githubuser1.datasource.local.dao

import androidx.room.*
import com.submission.githubuser1.datasource.remote.response.UserDetailResponse

/****************************************************
 * Created by Indra Muliana
 * On Wednesday, 03/11/2021 19.45
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

@Dao
interface UserDetailDao {

    @Query("SELECT * FROM user_details WHERE `id` = :key")
    suspend fun find(key: String): UserDetailResponse?

    @Query("SELECT * FROM user_details")
    suspend fun all(): List<UserDetailResponse>

    @Query("SELECT * FROM user_details WHERE is_favourite = 1")
    suspend fun getUserFavourite(): List<UserDetailResponse>

    @Query("SELECT * FROM user_details WHERE `id` LIKE '%' || :key || '%' AND `name` LIKE '%' || :key || '%' AND is_favourite = 1")
    suspend fun findUserFavourite(key: String): List<UserDetailResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: UserDetailResponse)

    @Delete
    suspend fun delete(value: UserDetailResponse)

    @Query("SELECT EXISTS (SELECT 1 FROM user_details WHERE `id` = :key)")
    suspend fun exist(key: String): Boolean

    @Query("UPDATE user_details SET `is_favourite` = :isFavourite WHERE `id` = :key")
    suspend fun setFavourite(key: String, isFavourite: Boolean): Int

    @Query("SELECT is_favourite FROM user_details WHERE `id` = :key")
    suspend fun isFavourite(key: String): Int

}