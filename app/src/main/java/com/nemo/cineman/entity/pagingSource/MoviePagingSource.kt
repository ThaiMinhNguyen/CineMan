package com.nemo.cineman.entity.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nemo.cineman.api.MovieService
import com.nemo.cineman.entity.ListType
import com.nemo.cineman.entity.Movie

class MoviePagingSource(
    private val movieService: MovieService,
    private val type: ListType
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {
            val response = when (type) {
                ListType.NowPlaying -> movieService.getNowPlayingMovies(page = page)
                ListType.Popular -> movieService.getPopularMovies(page = page)
                ListType.TopRated -> movieService.getTopRatedMovies(page = page)
                ListType.UpComing -> movieService.getUpComingMovies(page = page)
            }

            val movies = response.results

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (movies.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}
