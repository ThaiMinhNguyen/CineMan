package com.nemo.cineman.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nemo.cineman.api.MovieRepository
import com.nemo.cineman.api.UserRepository
import com.nemo.cineman.entity.DetailMovie
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieList
import com.nemo.cineman.entity.VideoResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _isFavourite = MutableLiveData<Boolean>(false)
    val isFavourite: LiveData<Boolean> get() = _isFavourite

    private val _isWatchlist = MutableLiveData<Boolean>(false)
    val isWatchlist: LiveData<Boolean> get() = _isWatchlist

    private val _ratedValue = MutableLiveData<Double>(0.0)
    val ratedValue: LiveData<Double> get() = _ratedValue

    private val _isRated = MutableLiveData<Boolean>(false)
    val isRated: LiveData<Boolean> get() = _isRated

    private val _message = MutableLiveData<String?>(null)
    val message : LiveData<String?> get() = _message


    fun onMessageHandled(){
        _message.value = null
    }

    fun getMovieDetail(id: Int){
        viewModelScope.launch {
            _isLoading.value = true
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
            _isLoading.value = true
            val result = movieRepository.getMovieTrailer(id)
            result.onSuccess { videos ->
                _videoResults.postValue(videos)
                Log.d("MyLog", "Fetch movie trailers: ${videos}")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to fetch movie trailer: ${exception.message}")
            }
            _isLoading.value = false
        }
    }

    fun checkFavourite(movieId: Int){
        viewModelScope.launch {
            _isLoading.value = true
            val result = userRepository.checkMovieFavourite(movieId)
            result.onSuccess { accountResponseState ->
                _isFavourite.value = accountResponseState.favorite
                _isWatchlist.value = accountResponseState.watchlist
                if (accountResponseState.rated == null){
                    _isRated.value = false
                } else {
                    _isRated.value = true
                    _ratedValue.value = accountResponseState.rated.value
                }
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to fetch account state: ${exception.message}")
            }
            _isLoading.value = false
        }
    }

    fun updateRating(movieId: Int, value: Double){
        viewModelScope.launch {
            _isLoading.value = true
            val result = userRepository.addRateToMovie(movieId, value)
            result.onSuccess { accountResponse ->
                _message.value = accountResponse.statusMessage
                Log.e("MyLog", "Add rating: ${accountResponse.statusMessage} / value: $value")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to add rating: ${exception.message}")
            }
            _ratedValue.value = value
            _isLoading.value = false
        }
    }

    fun toggleMovieToFavourite(movieId: Int){
        viewModelScope.launch {
            val favourite = _isFavourite.value?: false
            val result = userRepository.toggleMovieToFavourite(movieId, !favourite)
            result.onSuccess { accountResponse ->
                _message.value = accountResponse.statusMessage
                _isFavourite.value = !favourite
                Log.e("MyLog", "Add movie to favourite: ${accountResponse.statusMessage}")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to add movie to favourite: ${exception.message}")
            }
        }
    }

    fun toggleMovieToWatchlist(movieId: Int){
        viewModelScope.launch {
            val watchlist = _isWatchlist.value?: false
            val result = userRepository.toggleMovieToWatchlist(movieId, !watchlist)
            result.onSuccess { accountResponse ->
                _message.value = accountResponse.statusMessage
                _isWatchlist.value = !watchlist
                Log.e("MyLog", "Add movie to watchlist: ${accountResponse.statusMessage}")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to add movie to watchlist: ${exception.message}")
            }
        }
    }


    fun toggleSeriesToFavourite(movieId: Int){
        viewModelScope.launch {
            val favourite = _isFavourite.value?: false
            val result = userRepository.toggleSeriesToFavourite(movieId, !favourite)
            result.onSuccess { accountResponse ->
                _message.value = accountResponse.statusMessage
                _isFavourite.value = !favourite
                Log.e("MyLog", "Add movie to favourite: ${accountResponse.statusMessage}")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to add movie to favourite: ${exception.message}")
            }
        }
    }

    fun toggleSeriesToWatchlist(movieId: Int){
        viewModelScope.launch {
            val watchlist = _isWatchlist.value?: false
            val result = userRepository.toggleSeriesToWatchlist(movieId, !watchlist)
            result.onSuccess { accountResponse ->
                _message.value = accountResponse.statusMessage
                _isFavourite.value = !watchlist
                Log.e("MyLog", "Add movie to favourite: ${accountResponse.statusMessage}")
            }.onFailure { exception ->
                Log.e("MyLog", "Failed to add movie to favourite: ${exception.message}")
            }
        }
    }

    fun getUserList() : Flow<PagingData<MovieList>>{
        return userRepository.getAccountList().cachedIn(viewModelScope)
    }

    fun addMovieToList(movieId: Int, listId: Int){
        viewModelScope.launch {
            _isLoading.value = true
            val result = userRepository.addMovieToList(listId, movieId)
            result.onSuccess { response ->
                _message.value = response.statusMessage
                Log.e("MyLog", "Create new list: ${response.statusMessage}")
            }.onFailure { exception ->
                _message.value = "FAILED: ${exception.message}"
                Log.e("MyLog", "Failed to create new list: ${exception.localizedMessage}")
            }
            _isLoading.value = false
        }
    }
}