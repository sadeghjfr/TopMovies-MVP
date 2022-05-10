package com.sadeghjfr22.topmovies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class Movies(@PrimaryKey(autoGenerate = false)
                  val id : Int,
                  val title : String,
                  val poster : String,
                  val year : String,
                  val released : String,
                  val runtime : String,
                  val director : String,
                  val actors : String,
                  val plot : String,
                  val country : String,
                  val awards : String,
                  val imdb_rating : String,
                  val imdb_votes : String,
                  val type : String,
                  val genres : ArrayList<String>,
                  var watched: Boolean,
                  var showCheckBox: Boolean,
                  var checked: Boolean)