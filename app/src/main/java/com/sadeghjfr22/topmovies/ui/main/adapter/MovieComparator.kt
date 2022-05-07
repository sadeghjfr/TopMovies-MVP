package com.sadeghjfr22.topmovies.ui.main.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.sadeghjfr22.topmovies.data.model.Movies

object MovieComparator : DiffUtil.ItemCallback<Movies>() {
    override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
        return oldItem == newItem
    }
}