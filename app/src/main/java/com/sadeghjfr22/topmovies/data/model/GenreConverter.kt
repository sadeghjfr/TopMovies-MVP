package com.sadeghjfr22.topmovies.data.model

import androidx.room.TypeConverter

class GenreConverter {

    @TypeConverter
    fun gettingListFromString(genres: String): ArrayList<String> {
        val list = arrayListOf<String>()

        val array = genres.split(",".toRegex()).dropLastWhile {
            it.isEmpty()
        }.toTypedArray()

        for (s in array) {
            if (s.isNotEmpty()) {
                list.add(s)
            }
        }
        return list
    }

    @TypeConverter
    fun writingStringFromList(list: ArrayList<String>): String {
        var genres=""
        for (i in list) genres += ",$i"
        return genres
    }

}