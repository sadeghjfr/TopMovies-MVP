package com.sadeghjfr22.covid19.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sadeghjfr22.topmovies.data.remote.api.ApiHelper
import com.sadeghjfr22.topmovies.data.model.MovieModel
import com.sadeghjfr22.topmovies.data.model.Movies
import retrofit2.HttpException
import java.io.IOException

class MoviesDataSource(private val apiHelper: ApiHelper,
                       private val genre_id: Int,
                       private val query: String): PagingSource<Int, Movies>()  {

    val STARTING_PAGE_INDEX = 1
    lateinit var response: MovieModel

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movies> {

        val page = params.key ?: STARTING_PAGE_INDEX

        return try {

            if (genre_id == 0)

                if (query.equals(""))
                    response = apiHelper.getMovies(page)

                else
                    response = apiHelper.searchMovies(query, page)

            else
                response = apiHelper.getMoviesByGenre(genre_id, page)

            //////////

            val movies = response.data
            val metadata = response.metadata

            LoadResult.Page(
                data    = movies,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (metadata.page_count.toString() == metadata.current_page) null else page + 1
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movies>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
