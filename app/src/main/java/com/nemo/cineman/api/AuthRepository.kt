package com.nemo.cineman.api

import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.RequestTokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService
) {
    fun getRequestToken(callback: (Result<RequestTokenResponse?>) -> Unit) {
        val call = authService.getRequestToken()
        call.enqueue(object : Callback<RequestTokenResponse>{
            override fun onResponse(
                call: Call<RequestTokenResponse>,
                response: Response<RequestTokenResponse>
            ) {
                if (response.isSuccessful){
                    val requestTokenResponse = response.body()
                    callback(Result.success(requestTokenResponse))
                } else {
                    callback(Result.failure(Throwable("Error: ${response.errorBody()?.toString()}")))
                }
            }

            override fun onFailure(call: Call<RequestTokenResponse>, t: Throwable) {
                callback(Result.failure(t))
            }

        })
    }
}