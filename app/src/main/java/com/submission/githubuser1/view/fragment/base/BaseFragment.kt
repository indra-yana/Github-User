package com.submission.githubuser1.view.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.submission.githubuser1.repository.BaseRepository
import com.submission.githubuser1.view.viewmodel.ViewModelFactory

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 10/10/2021 09.29
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

abstract class BaseFragment<VB : ViewBinding, VM: ViewModel, BR: BaseRepository> :
    Fragment(),
    FragmentContract<VB, VM, BR>
{

    private var _viewBinding: VB? = null
    private var _viewModel: VM? = null

    override val viewBinding get() = _viewBinding!!
    override val viewModel get() = _viewModel!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this, ViewModelFactory(getRepository()))[getViewModel()]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = getViewBinding(inflater, container)
        return viewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

}