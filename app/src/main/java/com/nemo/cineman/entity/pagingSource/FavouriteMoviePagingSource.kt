package com.nemo.cineman.entity.pagingSource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nemo.cineman.api.UserService
import com.nemo.cineman.entity.Movie


class FavouriteMoviePagingSource(
    private val userService: UserService,
    private val language: String = "en-US",
    private val sessionId: String?,
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {
            if (sessionId == null) {
                Log.e("MyLog", "Error: Invalid session ID")
                return LoadResult.Error(IllegalStateException("Invalid session ID"))
            }
            
            Log.d("MyLog", "Loading favourite movie for session: $sessionId")

            val response = userService.getAllFavouriteMovie(
                page = page,
                sessionId = sessionId,
                language = language
            )

            val movies = response.results
            Log.d("MyLog", "Loaded ${movies.size} movies")

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.e("MyLog", "Error loading favourite movies", e)
            LoadResult.Error(e)
        }
    }
}