package com.sadeghjfr22.topmovies.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.sadeghjfr22.topmovies.R
import com.sadeghjfr22.topmovies.data.model.Genres
import com.sadeghjfr22.topmovies.ui.main.presenter.HomePresenterImpl
import com.sadeghjfr22.topmovies.ui.main.view.HomeView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GenreAdapter(var genres: ArrayList<Genres>,
                   var context: Context,
                   var presenter: HomePresenterImpl<HomeView>,
                   var movieAdapter: MovieAdapter,
                   var viewLifecycleOwner: LifecycleOwner): RecyclerView.Adapter<GenreAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_genre, parent,false)

        return MyViewHolder(itemView)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtTitle = itemView.findViewById<TextView>(R.id.txt_genre_title)
        var root     = itemView.findViewById<CardView>(R.id.genre_card)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.setIsRecyclable(false)
        val genre = genres.get(position)
        holder.txtTitle.setText(genre.name)
        setGenreBackground(genre, holder)

        holder.root.setOnClickListener {

            if (!genre.selected){

                genre.selected = true
                setGenreBackground(genre, holder)
                setNotSelectedGenres(genre)
                getMoviesByGenre(genre.id)
                notifyDataSetChanged()
            }
        }

    }

    private fun setNotSelectedGenres(selectedGenre: Genres){

        for (item in genres)
            if (item.id != selectedGenre.id)
                item.selected = false
    }

    private fun setGenreBackground(genre: Genres, holder: MyViewHolder){

        if (genre.selected)
            holder.root.setCardBackgroundColor(context.resources.getColor(R.color.colorPrimary))

        else
            holder.root.setCardBackgroundColor(context.resources.getColor(R.color.white))
    }

    private fun getMoviesByGenre(genre_id: Int){

        viewLifecycleOwner.lifecycleScope.launch {

            if (genre_id == 0){

                presenter.getMovies().collectLatest { movies->
                    movieAdapter.submitData(movies)
                }
            }

            else{

                presenter.getMoviesByGenre(genre_id).collectLatest { movies->
                    movieAdapter.submitData(movies)
                }
            }

        }

    }

    fun addGenres(categories: ArrayList<Genres>) {

        this.genres.apply {
            clear()
            addAll(categories)
        }
    }

    override fun getItemCount(): Int = genres.size

}