package com.submission.githubuser1.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.submission.githubuser1.R
import com.submission.githubuser1.view.fragment.FollowFragment

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 21.27
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class UserFollowFragmentAdapter(fragment: Fragment, private val key: String?) : FragmentStateAdapter(fragment) {

    val fragmentTitle = arrayListOf(
        fragment.getString(R.string.text_followers),
        fragment.getString(R.string.text_following),
    )

    override fun getItemCount(): Int {
        return fragmentTitle.size
    }

    override fun createFragment(position: Int): Fragment {
        return FollowFragment.newInstance(fragmentTitle[position], key)
    }

}