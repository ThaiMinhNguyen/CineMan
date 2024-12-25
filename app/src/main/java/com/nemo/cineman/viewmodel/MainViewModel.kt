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
    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>> get() = _nowPlayingMovies

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> get() = _popularMovies


//    init {
//        getMovies(1)
//    }

    fun getNowPlayingMovies(page: Int) {
        viewModelScope.launch {
            val result = movieRepository.fetchNowPlayingMovie(page)
            result.onSuccess { movies ->
                if (movies != null) {

                    _nowPlayingMovies.postValue(movies!!)
                } else {
                    Log.d("MyLog", "No movie fetched")
                }
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to fetch movies: ${exception.message}")
            }
            nowPlayingMovies.value?.let { movieRepository.insertLocalMovie(it) }
        }
    }

    fun getPopularMovies(page: Int) {
        viewModelScope.launch {
            val result = movieRepository.fetchPopularMovie(page)
            result.onSuccess { movies ->
                if (movies != null) {
                    _popularMovies.postValue(movies!!)
                } else {
                    Log.d("MyLog", "No movie fetched")
                }
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to fetch movies: ${exception.message}")
            }
        }
    }


    fun getMovieCertification(id: Int) {

        viewModelScope.launch {
            val result = movieRepository.fetchMovieCertification(id)
            result.onSuccess { movieCert ->

                Log.d("MyLog", "Fetch movie cert: ${movieCert.toString()}")
            }.onFailure { exception ->

                Log.d("MyLog", "Failed to fetch movieCert: ${exception.message}")
            }
        }

    }


}