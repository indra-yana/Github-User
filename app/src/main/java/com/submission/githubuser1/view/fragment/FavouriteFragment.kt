package com.submission.githubuser1.view.fragment

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.submission.githubuser1.R
import com.submission.githubuser1.databinding.FragmentFavouriteDialogBinding
import com.submission.githubuser1.datasource.remote.response.ResponseStatus
import com.submission.githubuser1.datasource.remote.response.User
import com.submission.githubuser1.helper.handleRequestError
import com.submission.githubuser1.helper.visible
import com.submission.githubuser1.listener.IOnItemClickListener
import com.submission.githubuser1.repository.UserRepository
import com.submission.githubuser1.view.adapter.UserAdapter
import com.submission.githubuser1.view.fragment.base.BaseBottomSheetDialogFragment
import com.submission.githubuser1.view.viewmodel.UserViewModel

class FavouriteFragment : BaseBottomSheetDialogFragment<FragmentFavouriteDialogBinding, UserViewModel, UserRepository>() {

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    private lateinit var adapter: UserAdapter

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentFavouriteDialogBinding.inflate(inflater, container, false)
    override fun getViewModel() = UserViewModel::class.java
    override fun getRepository() = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme)

        // fetchData()
        initAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.minimumHeight = Resources.getSystem().displayMetrics.heightPixels

        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.apply {
            peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
        }

        prepareUI()
        buildUserListRV()
        observeUserList()

        fetchData()
    }

    private fun prepareUI() {
        with(viewBinding) {
            tvBottomSheetTitle.text = getString(R.string.text_favourite)

            btnClose.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun initAdapter() {
        adapter = UserAdapter().apply {
            enableRemove = true
            iOnItemClickListener = object : IOnItemClickListener {
                override fun onItemClicked(data: User, position: Int) {
                    gotoDetail(data)
                }

                override fun onButtonRemoveItemClicked(data: User, position: Int) {
                    super.onButtonRemoveItemClicked(data, position)
                    adapter.onItemRemoved(position) {
                        viewModel.setFavourite(it.id, false)
                        Toast.makeText(requireContext(), "${it.login} removed from favourite!", Toast.LENGTH_SHORT).show()
                    }
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
        dismiss()

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
        viewModel.getFavourite()
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


//    @Suppress("UNCHECKED_CAST")
//    override fun <VB> getViewBinding(inflater: LayoutInflater, container: ViewGroup?) : VB {
//        return FragmentFavouriteDialogBinding.inflate(inflater, container, false) as VB
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <VM> getViewModel(): VM {
//        return UserViewModel::class.java as VM
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <BR> getRepository(): BR {
//        return UserRepository() as BR
//    }
}