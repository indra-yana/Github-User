package com.submission.githubuser1.helper

import androidx.recyclerview.widget.DiffUtil

/****************************************************
 * Created by Indra Muliana (indra.ndra26@gmail.com)
 * On Sunday, 10/10/2021 15.52
 * Email: indra.ndra26@gmail.com
 * Github: https://github.com/indra-yana
 ****************************************************/

class DiffUtils<O, N>(private val oldLIst: List<O>, private val newList: List<N>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldLIst.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldItemPosition == newItemPosition
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldLIst[oldItemPosition] == newList[newItemPosition]
}