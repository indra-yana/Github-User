package com.submission.githubuser1.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.submission.githubuser1.repository.BaseRepository
import com.submission.githubuser1.repository.UserRepository
import java.lang.IllegalArgumentException

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 18.55
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: BaseRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UserViewModel::class.java) -> UserViewModel(repository as UserRepository) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found!")
        }
    }
}