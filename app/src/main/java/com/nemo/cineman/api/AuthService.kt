package com.nemo.cineman.api

import com.nemo.cineman.entity.MovieResponse
import com.nemo.cineman.entity.RequestTokenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AuthService {

    @Headers(
        "accept: application/json"
    )
    @GET("authentication/token/new")
    fun getRequestToken(
    ): Call<RequestTokenResponse>


}