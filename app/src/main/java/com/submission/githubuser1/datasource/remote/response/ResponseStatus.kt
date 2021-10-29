package com.submission.githubuser1.datasource.remote.response

import okhttp3.ResponseBody

@Suppress("unused")
sealed class ResponseStatus<out T> {
    object Loading: ResponseStatus<Nothing>()
    data class Success<out T>(val value: T) : ResponseStatus<T>()
    data class Failure(val exception: Exception) : ResponseStatus<Nothing>()
    data class Error(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : ResponseStatus<Nothing>()
}