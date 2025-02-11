package com.nemo.cineman.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.nemo.cineman.entity.ListType
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieCertification
import com.nemo.cineman.entity.MovieDao
import com.nemo.cineman.entity.MoviePagingSource
import com.nemo.cineman.entity.SearchMoviePagingSource
import com.nemo.cineman.entity.VideoResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao
) {
    fun getLocalMovie() : List<Movie> {
       return movieDao.getAllMovie()
    }

    suspend fun insertLocalMovie(movies: List<Movie>) {
        withContext(Dispatchers.IO){
            movieDao.insertMovie(movies)
        }
    }

    suspend fun fetchNowPlayingMovie(page: Int): Result<List<Movie>?> {
        return try {
            val response = movieService.getNowPlayingMovies(language = "en-US", page = page)
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun fetchPopularMovie(page: Int) : Result<List<Movie>?> {
        return try {
            val response = movieService.getPopularMovies(language = "en-US", page = page)
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchTopRatedMovie(page: Int): Result<List<Movie>?> {
        return try {
            val response = movieService.getTopRatedMovies(language = "en-US", page = page)
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun fetchUpComingMovie(page: Int): Result<List<Movie>?> {
        return try {
            val response = movieService.getUpComingMovies(language = "en-US", page = page)
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getMoviesByType(type: ListType): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieService, type) }
        ).flow
    }

    fun searchMoviesByTitle(name: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchMoviePagingSource(movieService, name) }
        ).flow
    }

    suspend fun fetchMovieCertification(id: Int) : Result<MovieCertification?> {
        val response = movieService.getMovieCert(id).awaitResponse()
        try{
            if(response.isSuccessful){
                val movieCert = response.body()
                return Result.success(movieCert)
            } else {
                return Result.failure(Throwable("Error: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception){
            return Result.failure(Throwable(e))
        }
    }

    suspend fun fetchSimilarMovie(id: Int, page: Int) : Result<List<Movie>?>{
        val response = movieService.getSimilarMovie(id, page).awaitResponse()
        try{
            if(response.isSuccessful){
                val movies = response.body()?.results
                return Result.success(movies)
            } else {
                return Result.failure(Throwable("Error: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception){
            return Result.failure(Throwable(e))
        }
    }

    suspend fun fetchMovieTrailer(id: Int) : Result<List<VideoResult>?> {
        val response = movieService.getMovieTrailer(id).awaitResponse()
        try{
            if(response.isSuccessful){
                val videoResponse = response.body()?.results
                return Result.success(videoResponse)
            } else {
                return Result.failure(Throwable("Error: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception){
            return Result.failure(Throwable(e))
        }
    }

}