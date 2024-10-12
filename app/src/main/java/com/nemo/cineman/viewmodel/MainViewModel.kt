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

    private val _similarMovies = MutableLiveData<List<Movie>>()
    val similarMovies: LiveData<List<Movie>> get() = _similarMovies

//    init {
//        getMovies(1)
//    }

    fun getMovies(page: Int) {
        movieRepository.fetchMovie ({ result ->
            result.onSuccess { movies ->
                if (movies != null) {
                    movieRepository.insertLocalMovie(movies) // Lưu vào cơ sở dữ liệu
                    _movies.postValue(movies!!)
                } else {
                    Log.d("MyLog", "No movie fetched")
                }
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to fetch movies: ${exception.message}")
            }
        },page)
    }

    fun getMovieCertification(id: Int){
        var movieCertification : MovieCertification? = null
        viewModelScope.launch{
            movieRepository.fetchMovieCertification({ result ->
                result.onSuccess { movieCert ->
                    movieCertification = movieCert!!
                    Log.d("MyLog", "Fetch movie cert: ${movieCertification.toString()}")
                }.onFailure { exception ->
                    movieCertification = null
                    Log.d("MyLog", "Failed to fetch movieCert: ${exception.message}")
                }
            },id)
        }

    }

    fun getSimilarMovie(id: Int, page: Int) {
        viewModelScope.launch {
            movieRepository.fetchSimmilarMovie({result ->
                result.onSuccess { movies ->
                    _similarMovies.postValue(movies!!)
                    Log.d("MyLog", "Fetch similar movie: ${movies}")
                }.onFailure { exception ->
                    Log.e("MyLog", "Failed to fetch similar movie: ${exception.message}")
                }
            },id, page)
        }
    }
}