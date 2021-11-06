package com.submission.githubuser1.view.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuser1.databinding.FragmentSearchBinding
import com.submission.githubuser1.datasource.remote.response.ResponseStatus
import com.submission.githubuser1.datasource.remote.response.User
import com.submission.githubuser1.helper.handleRequestError
import com.submission.githubuser1.helper.visible
import com.submission.githubuser1.listener.IOnItemClickListener
import com.submission.githubuser1.repository.UserRepository
import com.submission.githubuser1.view.adapter.UserAdapter
import com.submission.githubuser1.view.fragment.base.BaseFragment
import com.submission.githubuser1.view.viewmodel.UserViewModel
import java.util.*


class SearchFragment : BaseFragment<FragmentSearchBinding, UserViewModel, UserRepository>() {

    companion object {
        private val TAG = this::class.java.simpleName
    }

    private lateinit var adapter: UserAdapter
    private var currentSearchQuery: String = ""

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentSearchBinding.inflate(inflater, container, false)
    override fun getViewModel() = UserViewModel::class.java
    override fun getRepository() = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    override fun onPause() {
        showInputMethod(false)
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareUI()
        buildUserListRV()
        observeUserSearch()
    }

    private fun prepareUI() {
        with(viewBinding) {
            btnBack.setOnClickListener {
                showInputMethod(false)
                findNavController().navigateUp()
            }

            val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        if (currentSearchQuery == query) {
                            return false
                        }

                        doSearch(query)
                        showInputMethod(false)

                        currentSearchQuery = query
                        return true
                    }

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        adapter.clearData()
                        return true
                    }

                    return false
                }
            })

            searchView.requestFocus()
            searchView.setOnQueryTextFocusChangeListener { _, show ->
                // TODO: handle Inputmethod method correctly
                showInputMethod(show)
            }
            searchView.setOnCloseListener {
                adapter.clearData()
                return@setOnCloseListener true
            }

            srlRefresh.setOnRefreshListener {
                adapter.clearData()
                doSearch(currentSearchQuery)
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

    private fun gotoDetail(user: User) {
        showInputMethod(false)
        val directions = SearchFragmentDirections.actionSearchFragmentToUserDetailFragment(user)
        findNavController().navigate(directions)
    }

    private fun buildUserListRV() {
        with(viewBinding) {
            rvUser.adapter = adapter
            rvUser.layoutManager = LinearLayoutManager(requireContext())
            rvUser.setHasFixedSize(true)
        }
    }

    private fun showInputMethod(show: Boolean) {
        val imm: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(if (show) InputMethodManager.SHOW_IMPLICIT else InputMethodManager.RESULT_HIDDEN, 0)
    }

    private fun observeUserSearch() {
        viewModel.userSearch.observe(viewLifecycleOwner, {
            toggleLoading(it is ResponseStatus.Loading)

            when (it) {
                is ResponseStatus.Loading -> {
                    Log.d(TAG, "observeUserSearch: Loading!")
                }
                is ResponseStatus.Success -> {
                    adapter.bindData(it.value.users)
                    Log.d(TAG, "observeUserSearch: Success!")
                }
                is ResponseStatus.Failure -> {
                    handleRequestError(it) { doSearch(currentSearchQuery) }
                    Log.d(TAG, "observeUserSearch: Failure!")
                }
            }
        })
    }

    private fun doSearch(q: String) {
        viewModel.userSearch(q)
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