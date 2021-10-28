package com.submission.githubuser1.view.adapter.viewholder

import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.submission.githubuser1.R
import com.submission.githubuser1.databinding.ItemCardFollowBinding
import com.submission.githubuser1.datasource.remote.response.User
import com.submission.githubuser1.listener.IOnItemClickListener

/****************************************************
 * Created by Indra Muliana
 * On Sunday, 24/10/2021 22.35
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class UserFollowViewHolder(private val binding: ItemCardFollowBinding) : BaseViewHolder(binding.root) {

    override fun bindItem(data: User, listener: IOnItemClickListener?) {
        with(binding) {
            tvItemTitle.text = data.login

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