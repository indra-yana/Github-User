package com.submission.githubuser1.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser1.databinding.ItemCardUserBinding
import com.submission.githubuser1.view.adapter.viewholder.BaseViewHolder
import com.submission.githubuser1.view.adapter.viewholder.UserViewHolder
import com.submission.githubuser1.datasource.remote.response.User
import com.submission.githubuser1.datasource.remote.response.UserResponse
import com.submission.githubuser1.helper.DiffUtils
import com.submission.githubuser1.listener.IOnItemClickListener

/****************************************************
 * Created by Indra Muliana
 * On Saturday, 09/10/2021 21.51
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList: UserResponse = UserResponse()
    var iOnItemClickListener: IOnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(ItemCardUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder).bindItem(itemList[position], iOnItemClickListener)
    }

    override fun getItemCount(): Int = itemList.size

    fun bindData(itemList: MutableList<User>) {
        val diffCallback = DiffUtils(this.itemList, itemList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.itemList.clear()
        this.itemList.addAll(itemList)

        diffResult.dispatchUpdatesTo(this)
    }

    fun clearData() {
        this.itemList.clear()
        notifyDataSetChanged()
    }
}