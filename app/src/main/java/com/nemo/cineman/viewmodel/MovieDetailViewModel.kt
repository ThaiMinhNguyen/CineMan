package com.nemo.cineman.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemo.cineman.api.MovieRepository
import com.nemo.cineman.api.UserRepository
import com.nemo.cineman.entity.DetailMovie
import com.nemo.cineman.entity.Genre
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.ProductionCompany
import com.nemo.cineman.entity.ProductionCountry
import com.nemo.cineman.entity.SpokenLanguage
import com.nemo.cineman.entity.VideoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailViewModel  @Inject constructor(
   private val movieRepository: MovieRepository,
   private val userRepository: UserRepository
) : ViewModel() {

    private val _movie = MutableLiveData<DetailMovie>()
    val movie: LiveData<DetailMovie> get() = _movie

    private val _similarMovies = MutableLiveData<List<Movie>>()
    val similarMovies: LiveData<List<Movie>> get() = _similarMovies

    private val _videoResults = MutableLiveData<List<VideoResult>>()
    val videoResults: LiveData<List<VideoResult>> get() = _videoResults

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isFavourite = MutableLiveData<Boolean>(false)
    val isFavourite: LiveData<Boolean> get() = _isFavourite

    private val _isWatchlist = MutableLiveData<Boolean>(false)
    val isWatchlist: LiveData<Boolean> get() = _isWatchlist

    private val _ratedValue = MutableLiveData<Double>(0.0)
    val ratedValue: LiveData<Double> get() = _ratedValue

    fun getMovieDetail(id: Int){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = movieRepository.getMovieDetail(id)
                result.onSuccess {
                    _movie.value = it
                    Log.d("MyLog", "Fetched movie: $it")
                }.onFailure {
                    Log.e("MyLog", "Failed to fetch movie: ${it.message}")
                }
            } finally {
                _isLoading.value = false
            }
        }
    }


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
            val result = movieRepository.getMovieTrailer(id)
            result.onSuccess { videos ->
                _videoResults.postValue(videos)
                Log.d("MyLog", "Fetch movie trailers: ${videos}")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to fetch movie trailer: ${exception.message}")
            }
        }
    }

    fun checkFavourite(movieId: Int, sessionId: String){
        viewModelScope.launch {
            val result = userRepository.checkMovieFavourite(movieId, sessionId)
            result.onSuccess { accountResponseState ->
                _isFavourite.value = accountResponseState.favorite
                _isWatchlist.value = accountResponseState.watchlist
                _ratedValue.value = accountResponseState.rated.value
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to fetch account state: ${exception.message}")
            }
        }
    }

    fun updateRating(value: Double){
        _ratedValue.value = value
    }

}