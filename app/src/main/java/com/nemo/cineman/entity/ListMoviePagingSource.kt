package com.nemo.cineman.entity

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nemo.cineman.api.UserService

class ListMoviePagingSource(
    private val userService: UserService,
    private val listId: Int,
    private val language: String = "en-US",
    private val sortBy: String? = null
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
            Log.d("MyLog", "Loading list movies for listId: $listId, page: $page, sort: $sortBy")
            
            val response = userService.getMoviesFromList(
                listId = listId,
                language = language,
                page = page,
                sortBy = sortBy
            )
            
            val movies = response.items
            Log.d("MyLog", "Loaded ${movies.size} movies from list $listId \n $movies")
            
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.e("MyLog", "Error loading list movies", e)
            LoadResult.Error(e)
        }
    }
}