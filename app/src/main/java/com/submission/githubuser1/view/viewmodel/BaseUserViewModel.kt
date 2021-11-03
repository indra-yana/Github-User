package com.submission.githubuser1.view.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 17.26
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

abstract class BaseUserViewModel : ViewModel() {
    abstract fun userList(context: Context): Job
    abstract fun userList(page: Int, perPage: Int): Job
    abstract fun userSearch(q: String): Job
    abstract fun userDetail(username: String): Job
    abstract fun userFollower(username: String): Job
    abstract fun userFollowing(username: String): Job
    abstract fun setFavourite(key: Int, isFavourite: Boolean): Job
}