package com.sadeghjfr22.topmovies.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sadeghjfr22.covid19.data.api.MoviesDataSource
import com.sadeghjfr22.topmovies.data.remote.api.ApiHelper
import com.sadeghjfr22.topmovies.data.model.Movies
import com.sadeghjfr22.topmovies.utils.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class MoviesRepo(private val apiHelper: ApiHelper) {

  fun getMovies(): Flow<PagingData<Movies>> {

    return Pager(
      config = PagingConfig(enablePlaceholders = true, pageSize = PAGE_SIZE),
      pagingSourceFactory = {
        MoviesDataSource(apiHelper, 0, "")
      }
    ).flow
  }

  fun searchMovies(query: String): Flow<PagingData<Movies>> {

    return Pager(
      config = PagingConfig(enablePlaceholders = true, pageSize = PAGE_SIZE),
      pagingSourceFactory = {
        MoviesDataSource(apiHelper, 0, query)
      }
    ).flow
  }

  fun getMoviesByGenre(genre_id: Int): Flow<PagingData<Movies>> {

    return Pager(
      config = PagingConfig(enablePlaceholders = true, pageSize = PAGE_SIZE),
      pagingSourceFactory = {
        MoviesDataSource(apiHelper, genre_id, "")
      }
    ).flow
  }

  suspend fun getMovieById(id: Int) = apiHelper.getMovieById(id)

  suspend fun getGenres() = apiHelper.getGenres()

}