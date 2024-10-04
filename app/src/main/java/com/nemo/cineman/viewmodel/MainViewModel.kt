package com.nemo.cineman.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemo.cineman.api.MovieRepository
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieCertification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    init {
        fetchMovies(1)
    }

    fun fetchMovies(page: Int) {
        movieRepository.fetchMovie ({ result ->
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
        },page)
    }

    fun getMovieCertification(id: Int) : MovieCertification?{
        var movieCertification : MovieCertification? = null
        viewModelScope.launch{
            movieRepository.getMovieCertification({ result ->
                result.onSuccess { movieCert ->
                    movieCertification = movieCert!!
                }.onFailure { exception ->
                    movieCertification = null
                    Log.e("MovieViewModel", "Failed to fetch movieCert: ${exception.message}")
                }
            },id)
        }
        return movieCertification
    }
}