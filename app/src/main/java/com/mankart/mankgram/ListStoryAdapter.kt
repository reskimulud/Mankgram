package com.mankart.mankgram

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mankart.mankgram.databinding.ItemRowStoryBinding

class ListStoryAdapter : RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {
    private var listStory = ArrayList<StoryModel>()

    class ListViewHolder(private val binding: ItemRowStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.tvName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemRowStoryBinding = ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemRowStoryBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val repo = listStory[position]

        holder.name.text = repo.name
    }

    override fun getItemCount(): Int = listStory.size

    fun setData(newData: ArrayList<StoryModel>) {
        listStory.clear()
        listStory.addAll(newData)
        notifyDataSetChanged()
    }
}