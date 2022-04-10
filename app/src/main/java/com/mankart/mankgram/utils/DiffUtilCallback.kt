package com.mankart.mankgram.utils

import androidx.recyclerview.widget.DiffUtil
import com.mankart.mankgram.model.StoryModel

class DiffUtilCallback(
    private val oldList: ArrayList<StoryModel>,
    private val newList: ArrayList<StoryModel>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].image != newList[newItemPosition].image -> false
            oldList[oldItemPosition].name != newList[newItemPosition].name -> false
            oldList[oldItemPosition].description != newList[newItemPosition].description -> false
            else -> true
        }
    }
}