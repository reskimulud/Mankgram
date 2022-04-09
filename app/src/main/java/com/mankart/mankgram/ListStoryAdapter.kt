package com.mankart.mankgram

import android.animation.ObjectAnimator
import android.content.res.Resources
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mankart.mankgram.databinding.ItemRowStoryBinding

class ListStoryAdapter : RecyclerView.Adapter<ListStoryAdapter.ListViewHolder>() {
    private var listStory = ArrayList<StoryModel>()

    inner class ListViewHolder(binding: ItemRowStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        var image = binding.storyImage
        var name = binding.storyName
        var description = binding.storyDescription
        var detail = binding.tvDetail
        var isExpanded = false

        val getResources: Resources = binding.root.context.resources
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemRowStoryBinding = ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(itemRowStoryBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val repo = listStory[position]

        holder.apply {
            name.text = repo.name
            Glide.with(itemView.context)
                .load("https://via.placeholder.com/${image.height}x${image.width}")
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(image)

            holder.detail.setOnClickListener {
                TransitionManager.beginDelayedTransition(itemView as ViewGroup)
                when (isExpanded) {
                    true -> {
                        description.visibility = View.GONE
                        detail.text = getResources.getString(R.string.detail)
                        detail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_keyboard_arrow_down_24, 0, 0, 0)
                        isExpanded = false
                    }
                    false -> {
                        description.visibility = View.VISIBLE
                        detail.text = getResources.getString(R.string.close)
                        detail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_keyboard_arrow_up_24, 0, 0, 0)
                        isExpanded = true
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = listStory.size

    fun setData(newData: ArrayList<StoryModel>) {
        listStory.clear()
        listStory.addAll(newData)
        notifyDataSetChanged()
    }
}