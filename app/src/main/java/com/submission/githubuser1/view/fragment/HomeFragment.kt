package com.submission.githubuser1.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser1.BaseApplication
import com.submission.githubuser1.R
import com.submission.githubuser1.databinding.FragmentHomeBinding
import com.submission.githubuser1.datasource.local.AppPreferences
import com.submission.githubuser1.datasource.remote.response.ResponseStatus
import com.submission.githubuser1.helper.handleRequestError
import com.submission.githubuser1.helper.visible
import com.submission.githubuser1.listener.IOnItemClickListener
import com.submission.githubuser1.model.User
import com.submission.githubuser1.repository.UserRepository
import com.submission.githubuser1.view.adapter.UserAdapter
import com.submission.githubuser1.view.fragment.base.BaseFragment
import com.submission.githubuser1.view.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding, UserViewModel, UserRepository>() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    private lateinit var adapter: UserAdapter

    // Indicator state.
    private var isLoading = false
    private var isNetworkError = false
    private var isRequestNextPage = false

    // Api paging
    private var perPage: Int = 10
    private var initialPage = 1
    private var nextPage = initialPage

    private var lastVisibleItem: Int = RecyclerView.NO_POSITION

    private val pref: AppPreferences by lazy { BaseApplication.pref }

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

    override fun prepareUI() = with(viewBinding) {
        layoutHeader.tvHeaderTitle.text = getString(R.string.text_home_title)
        layoutHeader.ivHeaderSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        srlRefresh.setOnRefreshListener {
            isLoading = false
            isNetworkError = false
            isRequestNextPage = false

            fetchData()
            // adapter.clearData()
        }

        fabFavourite.setOnClickListener {
            favouriteBottomSheet()
        }

        layoutHeader.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                pref.saveThemeSetting(isChecked)
            }
        }

        pref.getThemeSetting().asLiveData().observe(viewLifecycleOwner, { isChecked ->
            layoutHeader.switchTheme.isChecked = isChecked
            AppCompatDelegate.setDefaultNightMode(if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        })
    }

    private fun favouriteBottomSheet() {
        val favouriteBottomSheet = FavouriteFragment()
        favouriteBottomSheet.show(requireActivity().supportFragmentManager, FavouriteFragment.TAG)
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

    private fun buildUserListRV() = with(viewBinding) {
        rvUser.adapter = adapter
        rvUser.layoutManager = LinearLayoutManager(requireContext())
        rvUser.setHasFixedSize(true)
        rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()

                if (visibleItem > -1) {
                    lastVisibleItem = visibleItem
                }

                // Scroll down
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        isRequestNextPage = true

                        if (!isNetworkError) {
                            nextPage += perPage
                            viewModel.setPage(nextPage)
                        }

                        fetchData()
                        Log.d(TAG, "onScrolled: nextPage: $nextPage")
                    }
                }

                // Scroll up
                /*
                if (recyclerView.canScrollVertically(-1)) {
                    // isNetworkError = false
                }
                */
            }
        })
    }

    private fun gotoDetail(user: User) {
        val directions = HomeFragmentDirections.actionHomeFragmentToUserDetailFragment(user)
        findNavController().navigate(directions)
    }

    private fun observeUserList() {
        viewModel.users.observe(viewLifecycleOwner, {
            isLoading = it is ResponseStatus.Loading
            isNetworkError = it is ResponseStatus.Failure

            toggleLoading(isLoading)
            toggleNoData(isNetworkError)

            when (it) {
                is ResponseStatus.Loading -> {
                    Log.d(TAG, "observeUserList: Loading!")
                }
                is ResponseStatus.Success -> {
                    viewModel.setUserList(it.value)
                    Log.d(TAG, "observeUserList: Success!")
                }
                is ResponseStatus.Failure -> {
                    handleRequestError(it) { fetchData() }
                    Log.d(TAG, "observeUserList: Failure!")
                }
            }
        })

        viewModel.usersList.observe(viewLifecycleOwner, {
            toggleNoData(it.isNullOrEmpty())
            it?.let { users ->
                adapter.bindData(users)
            }
        })

        viewModel.page.observe(viewLifecycleOwner, {
            it?.let { page ->
                nextPage = page
            }
        })
    }

    override fun fetchData() {
         viewModel.userList(viewModel.getPage(), perPage)
    }

    override fun toggleLoading(isLoading: Boolean) = with(viewBinding) {
        srlRefresh.isRefreshing = isLoading
        shimmerContainer.showShimmer(isLoading && !isRequestNextPage)

        if (isLoading && !isRequestNextPage) {
            shimmerPlaceholder.root.visible(true)
            rvUser.visible(false)
        } else {
            shimmerContainer.stopShimmer()
            shimmerContainer.hideShimmer()

            shimmerPlaceholder.root.visible(false)
            rvUser.visible(true)
        }

    }

    override fun toggleNoData(isEmpty: Boolean) {
        viewBinding.tvNoData.visible(isEmpty)
        if (isEmpty) adapter.clearData()
    }
}