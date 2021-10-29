package com.submission.githubuser1.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuser1.R
import com.submission.githubuser1.databinding.FragmentHomeBinding
import com.submission.githubuser1.datasource.remote.response.ResponseStatus
import com.submission.githubuser1.datasource.remote.response.User
import com.submission.githubuser1.helper.handleRequestError
import com.submission.githubuser1.helper.visible
import com.submission.githubuser1.listener.IOnItemClickListener
import com.submission.githubuser1.repository.UserRepository
import com.submission.githubuser1.view.adapter.UserAdapter
import com.submission.githubuser1.view.viewmodel.UserViewModel
import kotlin.random.Random

class HomeFragment : BaseFragment<FragmentHomeBinding, UserViewModel, UserRepository>() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    private lateinit var adapter: UserAdapter
    private var initialPage: Int = Random.nextInt(1, 100)
    private var perPage: Int = 20

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentHomeBinding.inflate(inflater, container, false)
    override fun getViewModel() = UserViewModel::class.java
    override fun getRepository() = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
        initAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareUI()
        buildUserListRV()
        observeUserList()
    }

    private fun prepareUI() {
        with(viewBinding) {
            layoutHeader.tvHeaderTitle.text = getString(R.string.text_home_title)
            layoutHeader.ivHeaderSearch.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
            }

            srlRefresh.setOnRefreshListener {
                fetchData()
            }
        }
    }

    private fun initAdapter() {
        adapter = UserAdapter().apply {
            iOnItemClickListener = object : IOnItemClickListener {
                override fun onItemClicked(data: User, position: Int) {
                    gotoDetail(data)
                }
            }
        }
    }

    private fun buildUserListRV() {
        with(viewBinding) {
            rvUser.adapter = adapter
            rvUser.layoutManager = LinearLayoutManager(requireContext())
            rvUser.setHasFixedSize(true)
        }
    }

    private fun gotoDetail(user: User) {
        val directions = HomeFragmentDirections.actionHomeFragmentToUserDetailFragment(user)
        findNavController().navigate(directions)
    }

    private fun observeUserList() {
        viewModel.users.observe(viewLifecycleOwner, {
            toggleLoading(it is ResponseStatus.Loading)

            when (it) {
                is ResponseStatus.Loading -> {
                    Log.d(TAG, "observeUserList: Loading!")
                }
                is ResponseStatus.Success -> {
                    adapter.bindData(it.value)
                    Log.d(TAG, "observeUserList: Success!")
                }
                is ResponseStatus.Failure -> {
                    handleRequestError(it) { fetchData() }
                    Log.d(TAG, "observeUserList: Failure!")
                }
            }
        })
    }

    private fun fetchData() {
        viewModel.userList(initialPage, perPage)
    }

    private fun toggleLoading(isLoading: Boolean) {
        with(viewBinding) {
            srlRefresh.isRefreshing = isLoading
            shimmerContainer.showShimmer(isLoading)

            if (isLoading) {
                shimmerPlaceholder.root.visible(true)
                rvUser.visible(false)
            } else {
                shimmerContainer.stopShimmer()
                shimmerContainer.hideShimmer()

                shimmerPlaceholder.root.visible(false)
                rvUser.visible(true)
            }
        }
    }

}