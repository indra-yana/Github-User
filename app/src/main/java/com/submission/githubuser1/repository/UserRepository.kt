package com.submission.githubuser1.repository

import android.content.Context
import com.submission.githubuser1.datasource.remote.response.*
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
        remoteApi.detail(username)
    }

    suspend fun userFollower(username: String): ResponseStatus<FollowResponse> = safeApiCall {
        remoteApi.followers(username)
    }

    suspend fun userFollowing(username: String): ResponseStatus<FollowResponse> = safeApiCall {
        remoteApi.following(username)
    }

}