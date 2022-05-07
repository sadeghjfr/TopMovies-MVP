package com.sadeghjfr22.topmovies.api

import com.sadeghjfr22.topmovies.data.model.Genres
import com.sadeghjfr22.topmovies.data.model.MovieModel
import com.sadeghjfr22.topmovies.data.model.Movies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movies")
    suspend fun getMovies(@Query("page") page: Int): MovieModel

    @GET("movies")
    suspend fun searchMovies(@Query("q") q: String, @Query("page") page: Int): MovieModel

    @GET("genres/{genre_id}/movies")
    suspend fun getMoviesByGenre(@Path("genre_id") genre_id: Int, @Query("page") page: Int): MovieModel

    @GET("movies/{id}")
    suspend fun getMovieById(@Path("id") id: Int): Movies

    @GET("genres")
    suspend fun getGenres(): ArrayList<Genres>

}