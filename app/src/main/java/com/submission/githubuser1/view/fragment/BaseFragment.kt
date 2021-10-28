package com.submission.githubuser1.view.fragment

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

abstract class BaseFragment<VB : ViewBinding, VM: ViewModel, BR: BaseRepository> : Fragment() {

    protected lateinit var viewBinding: VB
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory(getRepository())).get(getViewModel())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = getViewBinding(inflater, container)
        return viewBinding.root
    }

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    abstract fun getViewModel(): Class<VM>
    abstract fun getRepository(): BR
}