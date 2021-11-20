package com.submission.githubuser1.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser1.databinding.ItemCardUserBinding
import com.submission.githubuser1.model.User
import com.submission.githubuser1.datasource.remote.response.UserResponse
import com.submission.githubuser1.helper.addAllFiltered
import com.submission.githubuser1.listener.IOnItemClickListener
import com.submission.githubuser1.view.adapter.viewholder.BaseViewHolder
import com.submission.githubuser1.view.adapter.viewholder.UserViewHolder

/****************************************************
 * Created by Indra Muliana
 * On Saturday, 09/10/2021 21.51
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList: UserResponse = UserResponse()
    var iOnItemClickListener: IOnItemClickListener? = null
    var enableRemove: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(ItemCardUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BaseViewHolder).apply {
            enableBtnItemRemove = enableRemove
            bindItem(itemList[position], iOnItemClickListener)
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun bindData(itemList: ArrayList<User>) {
        // The pagination in github user api sometime give the same list result
        // so in this case I use map filtering to avoid same data fetched or displayed in the list
        val oldCount: Int = itemCount
        this.itemList.addAllFiltered(itemList)
        notifyItemRangeInserted(oldCount, itemCount)
    }

    fun clearData() {
        this.itemList.clear()
        notifyDataSetChanged()
    }

    fun onItemRemoved(position: Int, callback: ((user: User) -> Unit)? = null) {
        if (position != RecyclerView.NO_POSITION) {
            callback?.let {
                it(itemList[position])
            }

            this.itemList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }
}