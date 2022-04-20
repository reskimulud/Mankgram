package com.mankart.mankgram.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mankart.mankgram.databinding.ItemLoadingBinding

class LoadingStateListStoryAdapter(private val retry : () -> Unit) : LoadStateAdapter<LoadingStateListStoryAdapter.LoadingStateViewHolder>() {

    inner class LoadingStateViewHolder(val binding: ItemLoadingBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }
    }

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        if (loadState is LoadState.Error) {
            holder.binding.errorMsg.text = loadState.error.localizedMessage
        }
        holder.binding.apply {
            progressBar.isVisible = loadState is LoadState.Loading
            retryButton.isVisible = loadState is LoadState.Error
            errorMsg.isVisible = loadState is LoadState.Error
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding, retry)
    }

}