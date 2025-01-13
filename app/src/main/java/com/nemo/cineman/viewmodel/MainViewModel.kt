package com.nemo.cineman.viewmodel

import android.app.Notification
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nemo.cineman.api.AuthRepository
import com.nemo.cineman.api.MovieRepository
import com.nemo.cineman.entity.ListType
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.RequestTokenBody
import com.nemo.cineman.entity.SessionResponse
import com.nemo.cineman.entity.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val authRepository: AuthRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {
    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>> get() = _nowPlayingMovies

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> get() = _popularMovies

    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>> get() = _topRatedMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> get() = _upcomingMovies

    private val _notificationEvent = MutableStateFlow<String?>(null)
    val notificationEvent : StateFlow<String?> = _notificationEvent


    fun onNotificationHandled(){
        _notificationEvent.value = null
    }

    fun onLogOutHandled(){
        sharedPreferenceManager.clearAll()
        Log.d("MyLog", "Wipe out all sharedPreferences/LogOut")
    }

    suspend fun checkSession(){
        val sessionId = sharedPreferenceManager.getSessionId()
        if(sessionId == null){
            val token = sharedPreferenceManager.getRequestToken()
            if (token != null){
                Log.d("MyLog", "checkSession: got request token: $token")
                var attempts = 0
                val maxRetries = 3
                var result: Result<SessionResponse?>? = null
                while (attempts < maxRetries && (result == null || result.isFailure == true)) {
                    result = authRepository.getNewSession(token)
                    attempts++
                    if (result.isFailure) {
                        delay(1000 * attempts.toLong())
                    }
                }
                if (result?.isSuccess == true) {
                    getNowPlayingMovies(1)
                    getPopularMovies()
                    val newSession = result.getOrNull()
                    newSession?.let {
                        it.sessionId?.let { it1 -> sharedPreferenceManager.saveSession(it1) }
                        Log.d("MyLog", "checkSession: New session created: ${it.sessionId}")
                    }
                } else {
                    val exception = result?.exceptionOrNull()
                    exception?.let {
                        Log.e("MyLog", "checkSession: Error creating session: ${it.message}")
                        //thông báo lỗi cho người dùng
                        _notificationEvent.value = "Không thể tạo session."
                    }
                }

            }

        } else {
            if(sharedPreferenceManager.isSessionExpired()){
                _notificationEvent.value = "Session hết hạn. Vui lòng đăng nhập lại."
                Log.e("MyLog", "checkSession: Session expired")
            } else {
                getNowPlayingMovies(1)
                getPopularMovies()
                Log.e("MyLog", "checkSession: Session still valid: $sessionId")
            }
        }
    }

    fun getMoviesByType(type: ListType): Flow<PagingData<Movie>> {
        return movieRepository.getMoviesByType(type).cachedIn(viewModelScope)
    }


    fun getNowPlayingMovies(page: Int = 1) {
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

    fun getPopularMovies(page: Int = 1) {
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