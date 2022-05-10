package com.sadeghjfr22.topmovies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sadeghjfr22.topmovies.data.local.dao.FavoritesDao
import com.sadeghjfr22.topmovies.data.model.GenreConverter
import com.sadeghjfr22.topmovies.data.model.Movies

@Database(entities = [Movies::class], version = 3)
@TypeConverters(GenreConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "APP_DB"
            )
                .build()
        }
    }
}