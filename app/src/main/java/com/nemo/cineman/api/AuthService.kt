package com.nemo.cineman.api

import com.nemo.cineman.entity.MovieResponse
import com.nemo.cineman.entity.RequestTokenBody
import com.nemo.cineman.entity.RequestTokenResponse
import com.nemo.cineman.entity.SessionResponse
import com.nemo.cineman.entity.UsernamePasswordBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @Headers(
        "accept: application/json"
    )
    @GET("authentication/token/new")
    fun getRequestToken(
    ): Call<RequestTokenResponse>

    @Headers(
        "accept: application/json"
    )
    @POST("authentication/session/new")
    suspend fun getNewSession(@Body requestTokenBody: RequestTokenBody) : SessionResponse

    @Headers(
        "accept: application/json"
    )
    @POST("authentication/token/validate_with_login")
    suspend fun getNewSessionWithLogin(@Body requestTokenBody: UsernamePasswordBody) : SessionResponse

}