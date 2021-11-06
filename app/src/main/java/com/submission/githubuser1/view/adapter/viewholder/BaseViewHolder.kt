package com.submission.githubuser1.view.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser1.datasource.remote.response.User
import com.submission.githubuser1.listener.IOnItemClickListener

/****************************************************
 * Created by Indra Muliana
 * On Saturday, 09/10/2021 21.51
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var enableBtnItemRemove: Boolean = false

    abstract fun bindItem(data: User, listener: IOnItemClickListener?)
}