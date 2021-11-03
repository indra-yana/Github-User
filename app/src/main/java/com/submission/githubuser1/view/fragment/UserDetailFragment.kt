package com.submission.githubuser1.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.githubuser1.R
import com.submission.githubuser1.databinding.FragmentUserDetailBinding
import com.submission.githubuser1.datasource.remote.response.ResponseStatus
import com.submission.githubuser1.datasource.remote.response.User
import com.submission.githubuser1.datasource.remote.response.UserDetail
import com.submission.githubuser1.helper.handleRequestError
import com.submission.githubuser1.helper.loadImage
import com.submission.githubuser1.helper.visible
import com.submission.githubuser1.repository.UserRepository
import com.submission.githubuser1.view.adapter.UserFollowFragmentAdapter
import com.submission.githubuser1.view.viewmodel.UserViewModel

class UserDetailFragment : BaseFragment<FragmentUserDetailBinding, UserViewModel, UserRepository>() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    private var user: User? = null

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentUserDetailBinding.inflate(inflater, container, false)
    override fun getViewModel() = UserViewModel::class.java
    override fun getRepository() = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = UserDetailFragmentArgs.fromBundle(arguments as Bundle).user
        fetchData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareUI()
        observeUserDetail()
    }

    private fun prepareUI() {
        with(viewBinding) {
            tvHeaderTitle.text = getString(R.string.text_profile)
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            srlRefresh.setOnRefreshListener {
                fetchData()
            }
        }
    }

    private fun updateUI(userDetail: UserDetail) {
        with(viewBinding) {
            userDetail.let {
                tvHeaderTitle.text = getString(R.string.text_profile)
                btnBack.setOnClickListener {
                    findNavController().navigateUp()
                }

                ivAvatar.loadImage(it.avatarUrl)
                tvRepositoryCount.text = it.publicRepos.toString()
                tvFollowersCount.text = it.followers.toString()
                tvFollowingCount.text = it.following.toString()
                tvItemTitle.text = it.name
                tvItemSubtitle.text = ("@${it.login}")
                tvItemLocation.text = it.location ?: getString(R.string.text_not_available)
                tvItemCompany.text = it.company ?: getString(R.string.text_not_available)
            }
        }
    }

    private fun buildUserFollowSection(username: String) {
        val userFollowFragmentAdapter = UserFollowFragmentAdapter(this, username)
        val viewPager: ViewPager2 = viewBinding.viewPager
        val tabs: TabLayout = viewBinding.tabs

        viewPager.adapter = userFollowFragmentAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = userFollowFragmentAdapter.fragmentTitle[position]
        }.attach()
    }

    private fun observeUserDetail() {
        viewModel.userDetail.observe(viewLifecycleOwner, {
            toggleLoading(it is ResponseStatus.Loading)

            when (it) {
                is ResponseStatus.Loading -> {
                    Log.d(TAG, "observeUserDetail: Loading!")
                }
                is ResponseStatus.Success -> {
                    with(it.value) {
                        updateUI(this)
                        buildUserFollowSection(login)
                    }

                    Log.d(TAG, "observeUserDetail: Success!")
                }
                is ResponseStatus.Failure -> {
                    handleRequestError(it) { fetchData() }
                    Log.d(TAG, "observeUserDetail: Failure!")
                }
            }
        })
    }

    private fun fetchData() {
        user?.let {
            viewModel.userDetail(it.login)
        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        with(viewBinding) {
            srlRefresh.isRefreshing = isLoading
            shimmerContainer.showShimmer(isLoading)

            if (isLoading) {
                shimmerPlaceholder.root.visible(true)
                userDetailContainer.visible(false)
            } else {
                shimmerContainer.stopShimmer()
                shimmerContainer.hideShimmer()

                shimmerPlaceholder.root.visible(false)
                userDetailContainer.visible(true)
            }
        }
    }

}