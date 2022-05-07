package com.sadeghjfr22.topmovies.ui.main.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sadeghjfr22.topmovies.R
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.databinding.AdapterMovieBinding
import com.sadeghjfr22.topmovies.utils.ImageLoader.loadImage

class MovieAdapter :
    PagingDataAdapter<Movies, MovieAdapter.MyViewHolder>(MovieComparator) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            AdapterMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    inner class MyViewHolder(private val binding: AdapterMovieBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movies) {

            binding.let {

                it.root.setOnClickListener {

                    val navController = Navigation.findNavController(it)
                    val bundle = Bundle()
                    bundle.putInt("ID", data.id)
                    navController.navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle)
                }

                it.txtMovieTitle.text = data.title
                it.txtMovieTitle.isSelected = true
                it.txtYear.text = data.year
                it.txtRate.text = data.imdb_rating
                it.imgPoster.loadImage(data.poster)
            }
        }
    }

}