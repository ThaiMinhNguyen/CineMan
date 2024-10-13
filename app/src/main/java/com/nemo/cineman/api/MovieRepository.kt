package com.nemo.cineman.api

import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieCertification
import com.nemo.cineman.entity.MovieDao
import com.nemo.cineman.entity.MovieResponse
import com.nemo.cineman.entity.VideoResponse
import com.nemo.cineman.entity.VideoResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    fun fetchNowPlayingMovie(callback: (Result<List<Movie>?>) -> Unit, page: Int) {
        val call = movieService.getNowPlayingMovies("en-US", page)
        call.enqueue(object: Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    val movies = response.body()?.results
                    callback(Result.success(movies))
                } else {

                    callback(Result.failure(Throwable("Error: ${response.errorBody()?.string()}")))
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                callback(Result.failure(t))
            }

        })
    }

    fun fetchPopularMovie(callback: (Result<List<Movie>?>) -> Unit, page: Int) {
        val call = movieService.getPopularMovies("en-US", page)
        call.enqueue(object: Callback<MovieResponse>{
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    val movies = response.body()?.results
                    callback(Result.success(movies))
                } else {

                    callback(Result.failure(Throwable("Error: ${response.errorBody()?.string()}")))
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                callback(Result.failure(t))
            }

        })
    }

    fun fetchMovieCertification(callback: (Result<MovieCertification?>) -> Unit, id: Int){
        val call = movieService.getMovieCert(id)
        call.enqueue(object: Callback<MovieCertification>{
            override fun onResponse(
                call: Call<MovieCertification>,
                response: Response<MovieCertification>
            ) {
                if(response.isSuccessful){
                    val movieCert = response.body()
                    callback(Result.success(movieCert))
                } else {
                    callback(Result.failure(Throwable("Error: ${response.errorBody()?.string()}")))
                }
            }

            override fun onFailure(call: Call<MovieCertification>, t: Throwable) {
                callback(Result.failure(t))
            }

        })
    }

    fun fetchSimmilarMovie(callback: (Result<List<Movie>?>) -> Unit, id: Int, page: Int){
        val call = movieService.getSimilarMovie(id, page)
        call.enqueue(object : Callback<MovieResponse>{
            override fun onResponse(p0: Call<MovieResponse>, response: Response<MovieResponse>) {
                if(response.isSuccessful){
                    val movies = response.body()?.results
                    callback(Result.success(movies))
                }else{
                    callback(Result.failure(Throwable("Error: $${response.errorBody()?.string()}")))
                }
            }

            override fun onFailure(p0: Call<MovieResponse>, p1: Throwable) {
                callback(Result.failure(p1))
            }

        })
    }

    fun fetchMovieTrailer(callback: (Result<List<VideoResult>?>) -> Unit, id: Int){
        val call = movieService.getMovieTrailer(id)
        call.enqueue(object: Callback<VideoResponse>{
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                if(response.isSuccessful){
                    val videoResponse = response.body()?.results
                    callback(Result.success(videoResponse))
                } else {
                    callback(Result.failure(Throwable("Error: $${response.errorBody()?.string()}")))
                }
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                callback(Result.failure(t))
            }

        })
    }

}