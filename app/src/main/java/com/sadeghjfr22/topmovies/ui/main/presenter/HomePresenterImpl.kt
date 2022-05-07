package com.sadeghjfr22.topmovies.ui.main.presenter

import androidx.lifecycle.liveData
import androidx.paging.PagingData
import com.sadeghjfr22.fooddelivery.utils.Resource
import com.sadeghjfr22.topmovies.api.ApiClient
import com.sadeghjfr22.topmovies.data.remote.api.ApiHelper
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.data.remote.repository.MoviesRepo
import com.sadeghjfr22.topmovies.ui.base.BasePresenterImpl
import com.sadeghjfr22.topmovies.ui.main.view.HomeView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class HomePresenterImpl<V: HomeView>() :
    BasePresenterImpl<V>(), HomePresenter<V> {

    private val moviesRepo = MoviesRepo(ApiHelper(ApiClient.getService()))

    override fun getMovies(): Flow<PagingData<Movies>> {

        return moviesRepo.getMovies()
    }

    override fun searchMovies(query: String): Flow<PagingData<Movies>> {

        return moviesRepo.searchMovies(query)
    }

    override fun getMoviesByGenre(genre_id: Int): Flow<PagingData<Movies>> {

        return moviesRepo.getMoviesByGenre(genre_id)
    }

    override fun getGenres() = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))

        try {
            emit(Resource.success(data = moviesRepo.getGenres()))
        } catch (exception: Exception) {
            emit(Resource.error(msg = exception.message.toString(), data = null ?: "Error Occurred!"))
        }
    }

}