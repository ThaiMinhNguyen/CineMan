package com.nemo.cineman.api

import com.nemo.cineman.entity.Account
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UserService {
    //==============Account Detail=============

    @Headers(
        "accept: application/json"
    )
    @GET("account/account_id")
    suspend fun getAccountDetail(
        @Query("session_id") sessionId: String
    ) : Account
}