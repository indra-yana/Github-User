package com.submission.githubuser1.helper

import com.submission.githubuser1.model.User
import com.submission.githubuser1.model.UserDetail
import com.submission.githubuser1.datasource.remote.response.UserResponse

/****************************************************
 * Created by Indra Muliana
 * On Wednesday, 03/11/2021 22.11
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

object DataMapper {
    fun userDetailsToUsersMapper(data: List<UserDetail>): UserResponse {
        val arrList = UserResponse()
        data.map {
            arrList.add(
                User(
                    id = it.id,
                    login = it.login,
                    avatarUrl = it.avatarUrl,
                    url = it.url,
                    reposUrl = "",
                )
            )
        }

        return arrList
    }

    fun booleanMapper(data: Int): Boolean {
        return data == 1
    }
}