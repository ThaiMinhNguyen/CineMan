package com.nemo.cineman.api

import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieDao
import com.nemo.cineman.entity.MovieResponse
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

    fun fetchMovie() : List<Movie>? {
        val call = movieService.getNowPlayingMovies("en-US", 1)
        return try {
            val response = call.execute()
            val movieList = response.body()?.results
            movieList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}