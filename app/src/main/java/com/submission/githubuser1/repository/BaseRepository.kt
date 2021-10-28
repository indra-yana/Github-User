package com.submission.githubuser1.repository

import com.submission.githubuser1.BaseApplication
import com.submission.githubuser1.datasource.remote.IApiEndPoint
import com.submission.githubuser1.datasource.remote.response.ResponseStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 15.35
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

abstract class BaseRepository {

    protected val remoteApi: IApiEndPoint by lazy { BaseApplication.remoteApi }

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResponseStatus<T> {
        return withContext(Dispatchers.IO) {
            try {
                ResponseStatus.Success(apiCall.invoke())
            } catch (exception: Exception) {
                ResponseStatus.Failure(exception)
            }
        }
    }

}