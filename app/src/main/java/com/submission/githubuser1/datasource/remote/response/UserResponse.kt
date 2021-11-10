package com.submission.githubuser1.datasource.remote.response

import android.annotation.SuppressLint
import android.os.Parcelable
import com.submission.githubuser1.model.User
import kotlinx.parcelize.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
class UserResponse : ArrayList<User>(), Parcelable