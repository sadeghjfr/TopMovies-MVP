package com.sadeghjfr22.topmovies.ui.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sadeghjfr22.topmovies.R
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.utils.ImageLoader.loadImage
import kotlinx.coroutines.*
import java.util.*

class FavoriteAdapter(val presenter:  FavoritesPresenterImpl<FavoritesView>,
                      var movies: ArrayList<Movies>,
                      var context: Context)
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
        var checkBox  = itemView.findViewById<CheckBox>(R.id.chbDelete)
        var root      = itemView.findViewById<LinearLayout>(R.id.rootFav)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val movie = movies.get(position)
        holder.setIsRecyclable(false)
        holder.checkBox.isVisible = movie.showCheckBox
        holder.checkBox.isChecked = movie.checked

        holder.txtTitle.setText(movie.title)
        holder.txtTitle.isSelected = true
        holder.txtYear.setText(movie.year)
        holder.txtRate.setText(movie.imdb_rating)
        holder.imgPoster.loadImage(movie.poster)

        holder.checkBox.setOnCheckedChangeListener { compoundButton, check ->
            movie.checked = check
            notifyDataSetChanged()
        }

        setWatchingStatusButton(movie.watched, holder)

        holder.btnWatch.setOnClickListener {
            setWatchingStatusButton(!movie.watched, holder)
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

    private fun setWatchingStatusButton(watched: Boolean, holder: MyViewHolder){

        if (watched) {

            holder.btnWatch.text = context.getString(R.string.watched)
            holder.btnWatch
                .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done, 0, 0, 0)
            holder.btnWatch.setBackgroundColor(context.resources.getColor(R.color.green))
        }

        else {

            holder.btnWatch.text = context.getString(R.string.to_watch)
            holder.btnWatch
                .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_to_watch, 0, 0, 0)
            holder.btnWatch.setBackgroundColor(context.resources.getColor(R.color.colorPrimary))
        }
    }

    fun showAllBoxes() {
        for (item in movies) {
            item.showCheckBox = true
        }
        notifyDataSetChanged()
    }

    fun hideAllBoxes() {
        for (item in movies) {
            item.showCheckBox = false
        }
        notifyDataSetChanged()
    }

    fun checkAllBoxes() {
        for (item in movies) {
            item.checked = true
        }
        notifyDataSetChanged()
    }

    fun unCheckAllBoxes() {
        for (item in movies) {
            item.checked = false
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = movies.size

}