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

    fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB
    fun getViewModel(): Class<VM>
    fun getRepository(): BR

}