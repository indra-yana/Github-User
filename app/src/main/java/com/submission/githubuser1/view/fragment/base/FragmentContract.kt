package com.submission.githubuser1.view.fragment.base

import android.view.LayoutInflater
import android.view.ViewGroup

/****************************************************
 * Created by Indra Muliana
 * On Saturday, 06/11/2021 10.41
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

interface FragmentContract<VB, VM, BR> {

    val viewBinding: VB
    val viewModel: VM

    /*
     * Required method to be contracted
    */
    fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    fun getViewModel(): Class<VM>
    fun getRepository(): BR

    /*
     * Optional method to be contracted
    */
    fun prepareUI() { }
    fun updateUI() { }
    fun toggleLoading(isLoading: Boolean) { }
    fun toggleNoData(isEmpty: Boolean) { }
    fun fetchData(page: Int) { }
    fun fetchData() { }

}