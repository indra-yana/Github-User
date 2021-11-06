package com.submission.githubuser1.view.adapter.viewholder

import com.submission.githubuser1.databinding.ItemCardUserBinding
import com.submission.githubuser1.datasource.remote.response.User
import com.submission.githubuser1.helper.loadImage
import com.submission.githubuser1.helper.visible
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
            ivItemThumbnail.loadImage(data.avatarUrl)

            root.setOnClickListener {
                listener?.onItemClicked(data, absoluteAdapterPosition)
            }

            if (enableBtnItemRemove) {
                btnRemove.visible(true)
                btnRemove.setOnClickListener {
                    listener?.onButtonRemoveItemClicked(data, absoluteAdapterPosition)
                }
            }
        }
    }
}