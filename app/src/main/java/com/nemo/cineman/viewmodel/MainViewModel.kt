package com.nemo.cineman.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nemo.cineman.api.MovieRepository
import com.nemo.cineman.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        movieRepository.fetchMovie { result ->
            result.onSuccess { movies ->
                if (movies != null) {
                    movieRepository.insertLocalMovie(movies) // Lưu vào cơ sở dữ liệu
                    _movies.postValue(movies!!)
                } else {
                    Log.d("MyLog", "No movie fetched")
                }
            }.onFailure { exception ->

                Log.e("MovieViewModel", "Failed to fetch movies: ${exception.message}")
            }
        }
    }
}