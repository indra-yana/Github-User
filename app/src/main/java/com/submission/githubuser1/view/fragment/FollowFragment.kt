package com.submission.githubuser1.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuser1.R
import com.submission.githubuser1.databinding.FragmentFollowBinding
import com.submission.githubuser1.datasource.remote.response.ResponseStatus
import com.submission.githubuser1.datasource.remote.response.User
import com.submission.githubuser1.helper.handleRequestError
import com.submission.githubuser1.helper.visible
import com.submission.githubuser1.listener.IOnItemClickListener
import com.submission.githubuser1.repository.UserRepository
import com.submission.githubuser1.view.adapter.UserFollowAdapter
import com.submission.githubuser1.view.viewmodel.UserViewModel

class FollowFragment : BaseFragment<FragmentFollowBinding, UserViewModel, UserRepository>() {

    private var username: String? = null
    private var title: String? = null

    private lateinit var adapter: UserFollowAdapter

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentFollowBinding.inflate(inflater, container, false)
    override fun getViewModel() = UserViewModel::class.java
    override fun getRepository() = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        username = arguments?.getString(ARG_FRAGMENT_KEY)
        title = arguments?.getString(ARG_FRAGMENT_TITLE)

        initAdapter()
        fetchData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buildUserFollowRV()
        observeUserFollow()
    }

    private fun initAdapter() {
        adapter = UserFollowAdapter().apply {
            iOnItemClickListener = object : IOnItemClickListener {
                override fun onItemClicked(data: User, position: Int) {
                    Toast.makeText(requireContext(), data.login, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun buildUserFollowRV() {
        with(viewBinding) {
            rvUser.adapter = adapter
            rvUser.layoutManager = LinearLayoutManager(requireContext())
            rvUser.setHasFixedSize(true)
        }
    }

    private fun observeUserFollow() {
        if (!title.isNullOrEmpty() && title == getString(R.string.text_followers)) {
            viewModel.userFollower.observe(viewLifecycleOwner, {
                toggleLoading(it is ResponseStatus.Loading)
                when (it) {
                    is ResponseStatus.Loading -> {
                        Log.d(TAG, "observeUserFollower: Loading!")
                    }
                    is ResponseStatus.Success -> {
                        adapter.bindData(it.value)
                        Log.d(TAG, "observeUserFollower: Success! ${it.value.size}")
                    }
                    is ResponseStatus.Failure -> {
                        handleRequestError(it) { fetchData() }
                        Log.d(TAG, "observeUserFollower: Failure!")
                    }
                }
            })
        }

        if (!title.isNullOrEmpty() && title == getString(R.string.text_following)) {
            viewModel.userFollowing.observe(viewLifecycleOwner, {
                toggleLoading(it is ResponseStatus.Loading)
                when (it) {
                    is ResponseStatus.Loading -> {
                        Log.d(TAG, "observeUserFollowing: Loading!")
                    }
                    is ResponseStatus.Success -> {
                        adapter.bindData(it.value)
                        Log.d(TAG, "observeUserFollowing: Success! ${it.value.size}")
                    }
                    is ResponseStatus.Failure -> {
                        handleRequestError(it) { fetchData() }
                        Log.d(TAG, "observeUserFollowing: Failure!")
                    }
                }
            })
        }
    }

    private fun fetchData() {
        username?.let { username ->
            if (!title.isNullOrEmpty() && title == getString(R.string.text_followers)) {
                viewModel.userFollower(username)
            } else if (!title.isNullOrEmpty() && title == getString(R.string.text_following)) {
                viewModel.userFollowing(username)
            } else {
                Log.d(TAG, "fetchData: No specified title!")
            }
        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        with(viewBinding) {
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

    companion object {
        private val TAG = this::class.java.simpleName
        private const val ARG_FRAGMENT_TITLE = "page_title"
        private const val ARG_FRAGMENT_KEY = "key_username"

        @JvmStatic
        fun newInstance(title: String, key: String?): FollowFragment {
            return FollowFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FRAGMENT_TITLE, title)
                    putString(ARG_FRAGMENT_KEY, key)
                }
            }
        }
    }

}