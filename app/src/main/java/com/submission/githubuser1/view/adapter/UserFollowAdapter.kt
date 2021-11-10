package com.submission.githubuser1.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser1.databinding.ItemCardFollowBinding
import com.submission.githubuser1.datasource.remote.response.FollowResponse
import com.submission.githubuser1.model.User
import com.submission.githubuser1.helper.DiffUtils
import com.submission.githubuser1.listener.IOnItemClickListener
import com.submission.githubuser1.view.adapter.viewholder.BaseViewHolder
import com.submission.githubuser1.view.adapter.viewholder.UserFollowViewHolder

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 22.35
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class UserFollowAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList: FollowResponse = FollowResponse()
    var iOnItemClickListener: IOnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserFollowViewHolder(ItemCardFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder).bindItem(itemList[position], iOnItemClickListener)
    }

    override fun getItemCount(): Int = itemList.size

    fun bindData(itemList: ArrayList<User>) {
        val diffCallback = DiffUtils(this.itemList, itemList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.itemList.clear()
        this.itemList.addAll(itemList)

        diffResult.dispatchUpdatesTo(this)
    }

}