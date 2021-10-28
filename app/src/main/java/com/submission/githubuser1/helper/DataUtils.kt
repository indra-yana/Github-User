package com.submission.githubuser1.helper

import android.content.Context
import com.submission.githubuser1.R
import com.submission.githubuser1.model.User

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 10/10/2021 14.54
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

object DataUtils {
    fun getUserList(context: Context): MutableList<User> {
        val data: MutableList<User> = mutableListOf()
        with(context.resources) {
            val name = getStringArray(R.array.name)
            val username = getStringArray(R.array.username)
            val company = getStringArray(R.array.company)
            val location = getStringArray(R.array.location)
            val repository = getIntArray(R.array.repository)
            val followers = getIntArray(R.array.followers)
            val following = getIntArray(R.array.following)
            val avatar = obtainTypedArray(R.array.avatar)

            for (i in name.indices) {
                data.add(
                    User(
                        name[i],
                        "@${username[i]}",
                        company[i],
                        location[i],
                        repository[i],
                        followers[i],
                        following[i],
                        avatar.getResourceId(i, -1)
                    )
                )
            }

            // avoiding memory leaks
            avatar.recycle()
        }

        return data
    }
}