package com.sadeghjfr22.topmovies.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.sadeghjfr22.fooddelivery.utils.State
import com.sadeghjfr22.topmovies.R
import com.sadeghjfr22.topmovies.data.local.AppDatabase
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.databinding.FragmentMovieDetailBinding
import com.sadeghjfr22.topmovies.ui.base.BaseFragment
import com.sadeghjfr22.topmovies.ui.base.BasePresenterImpl
import com.sadeghjfr22.topmovies.ui.base.BaseView
import com.sadeghjfr22.topmovies.utils.Constants.TAG
import com.sadeghjfr22.topmovies.utils.ImageLoader.loadImage
import kotlinx.coroutines.launch

class MovieDetailFragment: BaseFragment<BaseView, BasePresenterImpl<BaseView>>(), MovieDetailView {

    lateinit var binding: FragmentMovieDetailBinding
    lateinit var presenter: MovieDetailPresenterImpl<MovieDetailView>
    lateinit var movie: Movies
    private val dao by lazy { AppDatabase.getDatabase(requireContext()).favoritesDao() }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        presenter = MovieDetailPresenterImpl(dao)
        presenter.attachView(this)

        val movie_id = arguments?.getInt("ID")

        if (movie_id!=null) {

            getMovieById(movie_id)
            checkFavorites(movie_id)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed(it)
        }

        binding.btnFavorite.setOnClickListener {

            lifecycleScope.launch {

                if (presenter.existInFavorites(movie.id))
                    presenter.deleteFromFavorites(movie)

                else
                    presenter.insertToFavorites(movie)

                checkFavorites(movie.id)
            }
        }

        return binding.root
    }

    private fun getMovieById(id: Int){

        presenter.getMovieById(id).observe(viewLifecycleOwner, {

            it.let { resource ->

                when (resource.status) {

                    State.LOADING -> {
                        Log.e(TAG,"LOADING...:"+it.message.toString())
                        showProgress()
                        showError(false, "")
                    }

                    State.SUCCESS -> {
                        Log.e(TAG,"SUCCESS:"+it.message.toString())
                        resource.data.let { data ->
                            movie = data as Movies
                            showMovieDetail(movie) }
                        hideProgress()
                        showError(false,"")
                        binding.imgPicture.isVisible = true
                        binding.lytRoot.isVisible = true
                    }

                    State.ERROR -> {
                        Log.e(TAG,"ERROR:"+it.message.toString())
                        showError(true, it.message.toString())
                        hideProgress()
                    }

                }
            }
        })

    }

    private fun checkFavorites(id: Int){

        lifecycleScope.launch {

            if (presenter.existInFavorites(id))
                setFavoriteButtonResource(R.drawable.ic_favorite_fill)

            else
                setFavoriteButtonResource(R.drawable.ic_favorite)
        }
    }

    override fun showMovieDetail(movie: Movies) {

        binding.txtTitle.text = movie.title
        binding.txtActors.text = movie.actors
        binding.txtAwards.text = movie.awards
        binding.txtCountry.text = movie.country
        binding.txtDirector.text = movie.director
        binding.txtGenres.text = movie.genres.toString()
        binding.txtImdbRating.text = movie.imdb_rating
        binding.txtImdbVotes.text = movie.imdb_votes
        binding.txtPlot.text = movie.plot
        binding.txtReleased.text = movie.released
        binding.txtRuntime.text = movie.runtime
        binding.txtType.text = movie.type
        binding.txtYear.text = movie.year
        binding.imgPicture.loadImage(movie.poster)
    }

    override fun setFavoriteButtonResource(resource: Int) {
        binding.btnFavorite.setImageResource(resource)
    }

    override fun showError(visibility: Boolean, msg: String) {

        binding.txtError.isVisible = visibility
    }

    override fun showProgress() {
        binding.spinKit.isVisible = true
    }

    override fun hideProgress() {
        binding.spinKit.isVisible = false
    }

}