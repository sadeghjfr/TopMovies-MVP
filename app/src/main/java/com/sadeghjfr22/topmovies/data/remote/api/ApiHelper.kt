package com.sadeghjfr22.topmovies.data.remote.api

import com.sadeghjfr22.topmovies.api.ApiService

class ApiHelper(private val apiService: ApiService) {

    suspend fun getMovies(page: Int) = apiService.getMovies(page)

    suspend fun searchMovies(query: String, page: Int) = apiService.searchMovies(query, page)

    suspend fun getMoviesByGenre(genre_id: Int, page: Int) = apiService.getMoviesByGenre(genre_id, page)

    suspend fun getMovieById(id: Int) = apiService.getMovieById(id)

    suspend fun getGenres() = apiService.getGenres()

}