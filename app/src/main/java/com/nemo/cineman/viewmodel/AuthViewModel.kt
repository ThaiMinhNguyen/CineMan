package com.nemo.cineman.viewmodel

import androidx.lifecycle.ViewModel
import com.nemo.cineman.api.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private var requestToken : String = ""

    fun getRequestToken() {
        authRepository.getRequestToken { result ->
            result.onSuccess { requestTokenResponse ->
                requestToken = requestTokenResponse!!.requestToken
                println("Get Request Token Successfully")
            } .onFailure {
                println("Get Request Token Failed")
            }
        }
    }

}