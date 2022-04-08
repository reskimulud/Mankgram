package com.mankart.mankgram

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mankart.mankgram.databinding.ItemRowStoryBinding

class ListStoryAdapter : RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {
    private var listStory = ArrayList<StoryModel>()

    inner class ListViewHolder(binding: ItemRowStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        var image = binding.storyImage
        var name = binding.storyName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemRowStoryBinding = ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemRowStoryBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val repo = listStory[position]

        holder.name.text = repo.name
        Glide.with(holder.itemView.context)
            .load("https://via.placeholder.com/${holder.image.height}x${holder.image.width}")
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(holder.image)
    }

    override fun getItemCount(): Int = listStory.size

    fun setData(newData: ArrayList<StoryModel>) {
        listStory.clear()
        listStory.addAll(newData)
        notifyDataSetChanged()
    }
}