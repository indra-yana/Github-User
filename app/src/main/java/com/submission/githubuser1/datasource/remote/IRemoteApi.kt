package com.submission.githubuser1.datasource.remote

import com.submission.githubuser1.datasource.remote.response.FollowResponse
import com.submission.githubuser1.model.UserDetail
import com.submission.githubuser1.datasource.remote.response.UserSearchResponse
import com.submission.githubuser1.datasource.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 14.30
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

interface IRemoteApi {

    @GET("/users")
    suspend fun users(@Query("since") since: Int?, @Query("per_page") perPage: Int = 15): UserResponse

    @GET("/search/users")
    suspend fun search(@Query("q") query: String?): UserSearchResponse

    @GET("/users/{username}")
    suspend fun detail(@Path("username") username: String): UserDetail

    @GET("/users/{username}/followers")
    suspend fun followers(@Path("username") username: String): FollowResponse

    @GET("/users/{username}/following")
    suspend fun following(@Path("username") username: String): FollowResponse

}