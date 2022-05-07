package com.sadeghjfr22.topmovies.data.local.dao

import androidx.room.*
import com.sadeghjfr22.topmovies.data.model.Movies

@Dao
interface FavoritesDao {

    @Insert
    suspend fun insert(movie: Movies)

    @Update
    suspend fun update(movie: Movies)

    @Delete
    suspend fun delete(movie: Movies)

    @Query("select COUNT(*) from movies_table where id=:id")
    suspend fun exist(id :Int): Int

    @Query("delete from movies_table")
    suspend fun deleteAllFavorites()

    @Query("select * from movies_table")
    suspend fun getAllFavorites(): List<Movies>

}