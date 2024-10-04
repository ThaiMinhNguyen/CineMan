package com.nemo.cineman.api

import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieCertification
import com.nemo.cineman.entity.MovieDao
import com.nemo.cineman.entity.MovieResponse
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

    fun fetchMovie(callback: (Result<List<Movie>?>) -> Unit, page: Int) {
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

    suspend fun getMovieCertification(callback: (Result<MovieCertification?>) -> Unit, id: Int){
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
}