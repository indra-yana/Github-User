package com.submission.githubuser1.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.submission.githubuser1.datasource.remote.response.FollowResponse
import com.submission.githubuser1.datasource.remote.response.ResponseStatus
import com.submission.githubuser1.datasource.remote.response.UserResponse
import com.submission.githubuser1.datasource.remote.response.UserSearchResponse
import com.submission.githubuser1.helper.addAllFiltered
import com.submission.githubuser1.model.User
import com.submission.githubuser1.model.UserDetail
import com.submission.githubuser1.repository.UserRepository
import kotlinx.coroutines.launch

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 17.28
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class UserViewModel(private val repository: UserRepository) : BaseUserViewModel() {

    // Backing property
    private val _users: MutableLiveData<ResponseStatus<UserResponse>> = MutableLiveData()
    private val _userSearch: MutableLiveData<ResponseStatus<UserSearchResponse>> = MutableLiveData()
    private val _userDetail: MutableLiveData<ResponseStatus<UserDetail>> = MutableLiveData()
    private val _userFollower: MutableLiveData<ResponseStatus<FollowResponse>> = MutableLiveData()
    private val _userFollowing: MutableLiveData<ResponseStatus<FollowResponse>> = MutableLiveData()
    private val _isFavourite: MutableLiveData<ResponseStatus<Boolean>> = MutableLiveData()

    val users: LiveData<ResponseStatus<UserResponse>> get() = _users
    val userSearch: LiveData<ResponseStatus<UserSearchResponse>> get() = _userSearch
    val userDetail: LiveData<ResponseStatus<UserDetail>> get() = _userDetail
    val userFollower: LiveData<ResponseStatus<FollowResponse>> get() = _userFollower
    val userFollowing: LiveData<ResponseStatus<FollowResponse>> get() = _userFollowing
    val isFavourite: LiveData<ResponseStatus<Boolean>> get() = _isFavourite

    // Keep data alive
    private var _usersList: ArrayList<User> = arrayListOf()
    val usersList: MutableLiveData<ArrayList<User>> = MutableLiveData()
    val page: MutableLiveData<Int> = MutableLiveData(1)

    fun setUserList(itemList: ArrayList<User>) {
        usersList.value = _usersList.apply {
            addAllFiltered(itemList)
        }
    }

    fun setPage(page: Int) {
        this.page.value = page
    }

    fun getPage() = page.value ?: 1

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

    override fun setFavourite(key: Int, isFavourite: Boolean) = viewModelScope.launch {
        _isFavourite.value = ResponseStatus.Loading
        _isFavourite.value = repository.setFavourite(key, isFavourite)
    }

    override fun getFavourite() = viewModelScope.launch {
        _users.value = ResponseStatus.Loading
        _users.value = repository.getFavourite()
    }

}