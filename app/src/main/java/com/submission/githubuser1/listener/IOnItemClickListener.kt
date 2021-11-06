package com.submission.githubuser1.listener

import com.submission.githubuser1.datasource.remote.response.User


/****************************************************
 * Created by Indra Muliana
 * On Saturday, 09/10/2021 21.51
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

interface IOnItemClickListener {
    fun onItemClicked(data: User, position: Int)
    fun onButtonRemoveItemClicked(data: User, position: Int) { }
}