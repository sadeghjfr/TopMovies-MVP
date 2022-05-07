package com.sadeghjfr22.topmovies.ui.main.presenter

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.sadeghjfr22.fooddelivery.utils.Resource
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.ui.base.BasePresenter
import com.sadeghjfr22.topmovies.ui.main.view.HomeView
import kotlinx.coroutines.flow.Flow
import java.io.Serializable

interface HomePresenter<V: HomeView>: BasePresenter<V> {

    fun getMovies(): Flow<PagingData<Movies>>

    fun searchMovies(query: String): Flow<PagingData<Movies>>

    fun getMoviesByGenre(genre_id: Int): Flow<PagingData<Movies>>

    fun getGenres(): LiveData<Resource<Serializable>>

}