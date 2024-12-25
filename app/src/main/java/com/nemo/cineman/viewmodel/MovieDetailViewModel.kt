package com.nemo.cineman.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemo.cineman.api.MovieRepository
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.VideoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel  @Inject constructor(
   private val movieRepository: MovieRepository
) : ViewModel() {

    private val _similarMovies = MutableLiveData<List<Movie>>()
    val similarMovies: LiveData<List<Movie>> get() = _similarMovies

    private val _videoResults = MutableLiveData<List<VideoResult>>()
    val videoResults: LiveData<List<VideoResult>> get() = _videoResults


    fun getSimilarMovie(id: Int, page: Int) {
        viewModelScope.launch {
            val result = movieRepository.fetchSimilarMovie(id, page)
            result.onSuccess { movies ->
                _similarMovies.postValue(movies!!)
                Log.d("MyLog", "Fetch similar movie: ${movies}")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to fetch similar movie: ${exception.message}")
            }
        }
    }

    fun getMovieTrailer(id: Int){
        viewModelScope.launch {
            val result = movieRepository.fetchMovieTrailer(id)
            result.onSuccess { videos ->
                _videoResults.postValue(videos!!)
                Log.d("MyLog", "Fetch movie trailers: ${videos}")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to fetch movie trailer: ${exception.message}")
            }
        }
    }

}