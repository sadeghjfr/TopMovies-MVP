package com.sadeghjfr22.topmovies.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sadeghjfr22.topmovies.R
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.utils.ImageLoader.loadImage
import kotlinx.coroutines.*

class FavoriteAdapter(val presenter:  FavoritesPresenterImpl<FavoritesView>,
                      var movies: ArrayList<Movies>)
                      :RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_favorite, parent,false)

        return MyViewHolder(itemView)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtTitle  = itemView.findViewById<TextView>(R.id.txtMovieTitleFav)
        var txtYear   = itemView.findViewById<TextView>(R.id.txtYearFav)
        var txtRate   = itemView.findViewById<TextView>(R.id.txtRateFav)
        var imgPoster = itemView.findViewById<ImageView>(R.id.imgPosterFav)
        var btnWatch  = itemView.findViewById<Button>(R.id.btnWatch)
        var root      = itemView.findViewById<LinearLayout>(R.id.rootFav)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val movie = movies.get(position)
        holder.setIsRecyclable(false)

        holder.txtTitle.setText(movie.title)
        holder.txtTitle.isSelected = true
        holder.txtYear.setText(movie.year)
        holder.txtRate.setText(movie.imdb_rating)
        holder.imgPoster.loadImage(movie.poster)

        holder.btnWatch.setOnClickListener {
           updateWatchingStatus(movie)
        }

        holder.root.setOnClickListener {

            val navController = Navigation.findNavController(it)
            val bundle = Bundle()
            bundle.putInt("ID", movie.id)
            navController.navigate(R.id.action_favoritesFragment_to_movieDetailFragment, bundle)
        }

    }

    private fun updateWatchingStatus(movie: Movies){

        movie.watched = !movie.watched

        CoroutineScope(Dispatchers.IO).launch {

            presenter.updateFavorite(movie);
        }

    }

    override fun getItemCount(): Int = movies.size

}