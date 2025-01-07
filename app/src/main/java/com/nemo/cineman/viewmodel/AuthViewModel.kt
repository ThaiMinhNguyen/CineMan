package com.nemo.cineman.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemo.cineman.api.AuthRepository
import com.nemo.cineman.entity.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPreferenceManager: SharedPreferenceManager
) : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> get() = _isLoading
    private var _requestToken = MutableLiveData<String>().apply { value = null }
    val requestToken: LiveData<String> get() = _requestToken
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent


    suspend fun getRequestToken() : String? {
        _isLoading.value = true
        Log.d("MyLog", "isLoading value: ${_isLoading.value}")
        return try {
            val result = authRepository.getRequestToken()
            result.getOrNull()?.requestToken?.let { token ->
                val url = "https://www.themoviedb.org/authenticate/$token"
                _requestToken.value = token
                Log.d("MyLog", "Get Request Token Successfully")
                Log.d("MyLog", "Token: $token")
                Log.d("MyLog", "Requested Token On Success: $token")
                Log.d("MyLog", "Requested Token when press button: " + "${requestToken.value}/$token")
                Log.d("MyLog", url)
                _navigationEvent.value = url
                sharedPreferenceManager.saveToken(token)
                return url
            } ?: run {
                _error.postValue("Request token is null")
                Log.d("MyLog", "Request token is null")
                return null
            }
        } catch (e: Exception) {
            _error.postValue("Get Request Token Failed: ${e.message}")
            null
        } finally {
            _isLoading.value = false
        }
    }



    fun onNavigationHandled() {
        _navigationEvent.value = null
    }

}


