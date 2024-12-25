package com.nemo.cineman.api

import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieCertification
import com.nemo.cineman.entity.MovieDao
import com.nemo.cineman.entity.VideoResult
import retrofit2.awaitResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao
) {
    fun getLocalMovie() : List<Movie> {
       return movieDao.getAllMovie()
    }

    fun insertLocalMovie(movies: List<Movie>) {
        movieDao.insertMovie(movies)
    }

    suspend fun fetchNowPlayingMovie(page: Int): Result<List<Movie>?> {
        return try {
            val response = movieService.getNowPlayingMovies("en-US", page).awaitResponse()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                Result.success(movies)
            } else {
                val error = Throwable("Error: ${response.errorBody()?.string()}")
                Result.failure(error)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun fetchPopularMovie(page: Int) : Result<List<Movie>?> {
        val response = movieService.getPopularMovies("en-US", page).awaitResponse()
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