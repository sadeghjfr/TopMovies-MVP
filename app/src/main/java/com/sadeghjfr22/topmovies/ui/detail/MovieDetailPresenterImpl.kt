package com.sadeghjfr22.topmovies.ui.detail

import androidx.lifecycle.liveData
import com.sadeghjfr22.fooddelivery.utils.Resource
import com.sadeghjfr22.topmovies.api.ApiClient
import com.sadeghjfr22.topmovies.data.local.dao.FavoritesDao
import com.sadeghjfr22.topmovies.data.local.repository.FavoritesRepo
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.data.remote.api.ApiHelper
import com.sadeghjfr22.topmovies.data.remote.repository.MoviesRepo
import com.sadeghjfr22.topmovies.ui.base.BasePresenterImpl
import kotlinx.coroutines.Dispatchers

class MovieDetailPresenterImpl<V: MovieDetailView>(dao: FavoritesDao) :
    BasePresenterImpl<V>(), MovieDetailPresenter<V> {

    private val moviesRepo = MoviesRepo(ApiHelper(ApiClient.getService()))
    private val favoritesRepo = FavoritesRepo(dao)

    override fun getMovieById(id: Int) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = moviesRepo.getMovieById(id)))
        } catch (exception: Exception) {
            emit(Resource.error(msg = exception.message.toString(), data = null ?: "Error Occurred!"))
        }
    }

    override suspend fun existInFavorites(id: Int): Boolean {
        return favoritesRepo.exist(id)
    }

    override suspend fun insertToFavorites(movie: Movies) {
        favoritesRepo.insert(movie)
    }

    override suspend fun deleteFromFavorites(movie: Movies) {
        favoritesRepo.delete(movie)
    }

}