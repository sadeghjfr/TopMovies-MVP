package com.sadeghjfr22.topmovies.data.model

data class MovieModel(val data: List<Movies>,
                      val metadata: MetaData)

data class MetaData(val current_page: String,
                    val per_page: Int,
                    val page_count: Int,
                    val total_count: Int)

