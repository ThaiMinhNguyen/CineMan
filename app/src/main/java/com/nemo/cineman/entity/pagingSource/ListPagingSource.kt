package com.nemo.cineman.entity.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nemo.cineman.api.UserService
import com.nemo.cineman.entity.MovieList
import com.nemo.cineman.entity.SharedPreferenceManager

class ListPagingSource(
    private val userService: UserService,
    private val sharedPreferenceManager: SharedPreferenceManager
) : PagingSource<Int, MovieList>() {

    override fun getRefreshKey(state: PagingState<Int, MovieList>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieList> {
        val page = params.key?:1
        return try {
            val sessionId = sharedPreferenceManager.getSessionId()

            val response = if (sessionId != null) {
                userService.getAccountList(page = page, sessionId)

            } else {
                return LoadResult.Error(IllegalStateException("Invalid session"))
            }
            val list = response.results
            LoadResult.Page(
                data = list,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (list.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}