package com.nemo.cineman.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemo.cineman.api.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> get() = _isLoading
    private var _requestToken = MutableLiveData<String>().apply { value = null }
    val requestToken: LiveData<String> get() = _requestToken
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    suspend fun getRequestToken() : Result<String> = suspendCoroutine { continuation ->
        viewModelScope.launch {
            _isLoading.value = true
            Log.d("MyLog", "isLoading value:" + isLoading.value.toString())
            authRepository.getRequestToken { result ->
                result.onSuccess { requestTokenResponse ->
                    val token = requestTokenResponse!!.requestToken
                    _requestToken.value = token
                    Log.d("MyLog", "Get Request Token Successfully")
                    Log.d("MyLog", _requestToken.value!!)
                    println("Get Request Token Successfully")
                    _isLoading.value = false
                    Log.d("MyLog", "isLoading value:" + isLoading.value.toString())
                    continuation.resume(Result.success(token))
                }.onFailure { exception ->
                    Thread.sleep(10000)
                    _error.postValue("Get Request Token Failed")
                    _isLoading.value = false
                    Log.d("MyLog", "Get Request Token Failed: ${error.value}")
                    println("Get Request Token Failed")
                    continuation.resume(Result.failure(exception))
                }
            }

        }
    }

}
