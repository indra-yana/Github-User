package com.submission.githubuser1.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/****************************************************
 * Created by Indra Muliana
 * On Saturday, 09/10/2021 21.51
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

@Parcelize
data class User(
    val name: String,
    val username: String,
    val company: String,
    val location: String,
    val repository: Int,
    val followers: Int,
    val following: Int,
    val avatar: Int
) : Parcelable