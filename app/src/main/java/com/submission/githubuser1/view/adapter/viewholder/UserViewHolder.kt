package com.submission.githubuser1.view.adapter.viewholder

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.submission.githubuser1.R
import com.submission.githubuser1.databinding.ItemCardUserBinding
import com.submission.githubuser1.datasource.remote.response.User
import com.submission.githubuser1.listener.IOnItemClickListener

/****************************************************
 * Created by Indra Muliana
 * On Saturday, 09/10/2021 21.51
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class UserViewHolder(private val binding: ItemCardUserBinding) : BaseViewHolder(binding.root) {

    override fun bindItem(data: User, listener: IOnItemClickListener?) {
        with(binding) {
            tvItemTitle.text = data.login
            tvItemSubtitle.text = ("@${data.login}")
            tvUserLink.text = data.url

            Glide.with(root.context)
                    .load(data.avatarUrl)
                    .placeholder(ContextCompat.getDrawable(root.context, R.drawable.img_placeholder))
                    .into(ivItemThumbnail)

            root.setOnClickListener {
                listener?.onItemClicked(data, absoluteAdapterPosition)
            }
        }
    }
}