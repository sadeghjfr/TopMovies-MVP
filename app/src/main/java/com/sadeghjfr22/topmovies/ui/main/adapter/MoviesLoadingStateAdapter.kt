package com.sadeghjfr22.topmovies.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sadeghjfr22.topmovies.databinding.NetworkStateItemBinding

class MoviesLoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MoviesLoadingStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {

        val progress = holder.binding.progressBarItem
        val retryBtn = holder.binding.retryBtn

        if (loadState is LoadState.Loading) {
            progress.isVisible = true
            retryBtn.isVisible = false

        } else {
            progress.isVisible = false
        }

        if (loadState is LoadState.Error) {
            retryBtn.isVisible = true
        }


        retryBtn.setOnClickListener {
            retry.invoke()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    inner class LoadStateViewHolder(val binding: NetworkStateItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}