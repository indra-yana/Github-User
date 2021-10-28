package com.submission.githubuser1.datasource.remote.response


import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val users: ArrayList<User>,
    @SerializedName("total_count")
    val totalCount: Int
)