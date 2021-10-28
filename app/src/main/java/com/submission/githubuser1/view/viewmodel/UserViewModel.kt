package com.submission.githubuser1.view.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.submission.githubuser1.datasource.remote.response.*
import com.submission.githubuser1.model.User
import com.submission.githubuser1.repository.UserRepository
import kotlinx.coroutines.launch

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 17.28
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class UserViewModel(private val repository: UserRepository) : BaseUserViewModel() {

    private val _users: MutableLiveData<ResponseStatus<UserResponse>> = MutableLiveData()
    private val _userList: MutableLiveData<ResponseStatus<MutableList<User>>> = MutableLiveData()
    private val _userSearch: MutableLiveData<ResponseStatus<UserSearchResponse>> = MutableLiveData()
    private val _userDetail: MutableLiveData<ResponseStatus<UserDetailResponse>> = MutableLiveData()
    private val _userFollower: MutableLiveData<ResponseStatus<FollowResponse>> = MutableLiveData()
    private val _userFollowing: MutableLiveData<ResponseStatus<FollowResponse>> = MutableLiveData()
    private val _isLoading: MutableLiveData<ResponseStatus<Boolean>> = MutableLiveData()

    val users: LiveData<ResponseStatus<UserResponse>> = _users
    val userList: LiveData<ResponseStatus<MutableList<User>>> = _userList
    val userSearch: LiveData<ResponseStatus<UserSearchResponse>> = _userSearch
    val userDetail: LiveData<ResponseStatus<UserDetailResponse>> = _userDetail
    val userFollower: LiveData<ResponseStatus<FollowResponse>> = _userFollower
    val userFollowing: LiveData<ResponseStatus<FollowResponse>> = _userFollowing
    val isLoading: LiveData<ResponseStatus<Boolean>> = _isLoading

    override fun userList(context: Context) = viewModelScope.launch {
        _userList.value = ResponseStatus.Loading
        _userList.value = repository.userList(context)
    }

    override fun userList(page: Int, perPage: Int) = viewModelScope.launch {
        _users.value = ResponseStatus.Loading
        _users.value = repository.userList(page, perPage)
    }

    override fun userSearch(q: String) = viewModelScope.launch {
        _userSearch.value = ResponseStatus.Loading
        _userSearch.value = repository.userSearch(q)
    }

    override fun userDetail(username: String) = viewModelScope.launch {
        _userDetail.value = ResponseStatus.Loading
        _userDetail.value = repository.userDetail(username)
    }

    override fun userFollower(username: String) = viewModelScope.launch {
        _userFollower.value = ResponseStatus.Loading
        _userFollower.value = repository.userFollower(username)
    }

    override fun userFollowing(username: String) = viewModelScope.launch {
        _userFollowing.value = ResponseStatus.Loading
        _userFollowing.value = repository.userFollowing(username)
    }
}