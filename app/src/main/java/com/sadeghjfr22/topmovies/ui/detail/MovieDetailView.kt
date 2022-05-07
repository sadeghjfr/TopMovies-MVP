package com.sadeghjfr22.topmovies.ui.detail

import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.ui.base.BaseView

interface MovieDetailView: BaseView {

    fun showMovieDetail(movie: Movies)

    fun setFavoriteButtonResource(resource: Int)

}