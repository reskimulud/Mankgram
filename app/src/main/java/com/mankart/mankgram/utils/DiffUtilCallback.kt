package com.mankart.mankgram.utils

import androidx.recyclerview.widget.DiffUtil
import com.mankart.mankgram.model.StoryModel

class DiffUtilCallback: DiffUtil.ItemCallback<StoryModel>() {
    override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
        return when {
            oldItem.id != newItem.id -> false
            oldItem.image != newItem.image -> false
            oldItem.name != newItem.name -> false
            oldItem.description != newItem.description -> false
            oldItem.lat != newItem.lat -> false
            oldItem.lon != newItem.lon -> false
            else -> true
        }
    }
}