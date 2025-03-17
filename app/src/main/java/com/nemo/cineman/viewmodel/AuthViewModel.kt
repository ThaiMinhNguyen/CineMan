package com.nemo.cineman.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemo.cineman.api.AuthRepository
import com.nemo.cineman.entity.SharedPreferenceManager
import com.nemo.cineman.entity.UsernamePasswordBody
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
    private var _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> get() = _error
    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent

    fun onErrorHandled(){
        _error.value = null
    }

    fun initApplication(){
        viewModelScope.launch {
            _isLoading.value = true
            if(!sharedPreferenceManager.isSessionExpired()){
                _navigationEvent.value = "menu"
            }
            _isLoading.value = false
        }
    }

    suspend fun signInWithGuest(){
        _isLoading.value = true
        try {
            val result = authRepository.getNewGuestSession()
            result.getOrNull()?.let { result ->
                sharedPreferenceManager.saveGuestSession(result.guestSessionId, result.expiresAt)
                Log.d("MyLog", "Get Guest Session Successfully")
                Log.d("MyLog", "Guest Session: ${result.guestSessionId}")
                _navigationEvent.value = "menu"
            }?: run {
                _error.postValue("Login with guest Failed: session null")
                Log.d("MyLog", "Login with guest Failed: session null")
            }
        } catch (e: Exception){
            _error.postValue("Login with guest Failed: ${e.message}")
            Log.d("MyLog", "Login with guest Failed: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun signInWithLogin(userName : String, password : String) : Boolean {
        _isLoading.value = true
        Log.d("MyLog", "isLoading value: ${_isLoading.value}")
        return try {
            val result = authRepository.getRequestToken()
            result.getOrNull()?.requestToken?.let { token ->
                _requestToken.value = token
                Log.d("MyLog", "Get Request Token Successfully")
                Log.d("MyLog", "Token: $token")
                Log.d("MyLog", "Requested Token On Success: $token")
                Log.d("MyLog", "Requested Token when press button: " + "${requestToken.value}/$token")
                sharedPreferenceManager.saveToken(token)

                val res = authRepository.getNewSessionWithLogin(userName, password, token)
                if(res.getOrNull()?.success == true){
                    _navigationEvent.value = "menu"
                    return true
                }
                _error.postValue("Cannot login. Please check your username and password")
                return false
            } ?: run {
                _error.postValue("Request token is null")
                Log.d("MyLog", "Request token is null")
                return false
            }
        } catch (e: Exception) {
            _error.postValue("Get Request Token Failed: ${e.message}")
            false
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun signInWithTMDB() : String? {
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
                val encodedUrl = Uri.encode(url) // Encode URL trước khi điều hướng
                _navigationEvent.value = "webview/$encodedUrl"
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


