package com.sadeghjfr22.topmovies.data.local.repository

import com.sadeghjfr22.topmovies.data.local.dao.FavoritesDao
import com.sadeghjfr22.topmovies.data.model.Movies

class FavoritesRepo(private val favoritesDao: FavoritesDao) {

    suspend fun getAllFavorites(): List<Movies> = favoritesDao.getAllFavorites()

    suspend fun deleteAllFavorites() = favoritesDao.deleteAllFavorites()

    suspend fun insert(movie: Movies) = favoritesDao.insert(movie)

    suspend fun update(movie: Movies) = favoritesDao.update(movie)

    suspend fun delete(movie: Movies) = favoritesDao.delete(movie)

    suspend fun exist(id: Int): Boolean = favoritesDao.exist(id)>0
}