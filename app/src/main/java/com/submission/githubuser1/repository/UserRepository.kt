package com.submission.githubuser1.repository

import android.content.Context
import com.submission.githubuser1.datasource.remote.response.*
import com.submission.githubuser1.helper.DataMapper
import com.submission.githubuser1.helper.DataUtils
import com.submission.githubuser1.model.User

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 17.11
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class UserRepository : BaseRepository() {

    suspend fun userList(context: Context): ResponseStatus<MutableList<User>> = safeApiCall {
        // Dummy users from local assets
        DataUtils.getUserList(context)
    }

    suspend fun userList(page: Int, perPage: Int): ResponseStatus<UserResponse> = safeApiCall {
        remoteApi.users(page, perPage)
    }

    suspend fun userSearch(q: String): ResponseStatus<UserSearchResponse> = safeApiCall {
        remoteApi.search(q)
    }

    suspend fun userDetail(username: String): ResponseStatus<UserDetail> = safeApiCall {
        val dao = appDB.getUserDetailDao()
        val cache = dao.find(username)

        if (cache != null) {
            cache
        } else {
            val apiResult = remoteApi.detail(username)
            dao.insert(apiResult)

            apiResult
        }
    }

    suspend fun userFollower(username: String): ResponseStatus<FollowResponse> = safeApiCall {
        remoteApi.followers(username)
    }

    suspend fun userFollowing(username: String): ResponseStatus<FollowResponse> = safeApiCall {
        remoteApi.following(username)
    }

    suspend fun setFavourite(key: Int, isFavourite: Boolean): ResponseStatus<Boolean> = safeApiCall {
        val dao = appDB.getUserDetailDao()
        dao.setFavourite(key, isFavourite)

        DataMapper.booleanMapper(dao.isFavourite(key))
    }

}