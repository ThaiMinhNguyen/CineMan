package com.nemo.cineman.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nemo.cineman.api.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    var requestToken: String = null.toString()
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getRequestToken() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            Log.d("MyLog", isLoading.value.toString())
            authRepository.getRequestToken { result ->
                result.onSuccess { requestTokenResponse ->
                    requestToken = requestTokenResponse!!.requestToken
                    Log.d("MyLog", "Get Request Token Successfully")
                    Log.d("MyLog", requestToken)
                    println("Get Request Token Successfully")
                    _isLoading.postValue(false)
                    Log.d("MyLog", isLoading.value.toString())
                }.onFailure {
                    Thread.sleep(10000)
                    _error.postValue("Get Request Token Failed")
                    _isLoading.postValue(false)
                    Log.d("MyLog", "Get Request Token Failed")
                    println("Get Request Token Failed")
                }
            }

        }
    }

}
