package com.sadeghjfr22.topmovies.ui.favorites

import androidx.lifecycle.liveData
import com.sadeghjfr22.fooddelivery.utils.Resource
import com.sadeghjfr22.topmovies.data.local.dao.FavoritesDao
import com.sadeghjfr22.topmovies.data.local.repository.FavoritesRepo
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.ui.base.BasePresenterImpl
import kotlinx.coroutines.Dispatchers

class FavoritesPresenterImpl<V: FavoritesView>(dao: FavoritesDao) :
    BasePresenterImpl<V>(), FavoritesPresenter<V> {

    private val favoritesRepo = FavoritesRepo(dao)

    override fun getAllFavorites() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = favoritesRepo.getAllFavorites()))
        } catch (exception: Exception) {
            emit(Resource.error(msg = exception.message.toString(), data = null ?: "Error Occurred!"))
        }
    }

    override suspend fun deleteAllFavorites() = favoritesRepo.deleteAllFavorites()

    override suspend fun deleteFromFavorites(movie: Movies) = favoritesRepo.delete(movie)

    override suspend fun updateFavorite(movie: Movies) = favoritesRepo.update(movie)

}