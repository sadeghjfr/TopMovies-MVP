package com.sadeghjfr22.topmovies.ui.detail

import androidx.lifecycle.LiveData
import com.sadeghjfr22.fooddelivery.utils.Resource
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.ui.base.BasePresenter

interface MovieDetailPresenter<V: MovieDetailView>: BasePresenter<V> {

    fun getMovieById(id: Int): LiveData<Resource<Any>>

    suspend fun existInFavorites(id: Int): Boolean

    suspend fun insertToFavorites(movie: Movies)

    suspend fun deleteFromFavorites(movie: Movies)

}