package com.sadeghjfr22.topmovies.ui.favorites

import androidx.lifecycle.LiveData
import com.sadeghjfr22.fooddelivery.utils.Resource
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.ui.base.BasePresenter

interface FavoritesPresenter<V: FavoritesView>: BasePresenter<V> {

    fun getAllFavorites(): LiveData<Resource<Any>>

    suspend fun deleteAllFavorites()

    suspend fun deleteFromFavorites(movie: Movies)

    suspend fun updateFavorite(movie: Movies)

}