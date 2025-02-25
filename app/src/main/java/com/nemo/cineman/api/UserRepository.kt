package com.nemo.cineman.api

import android.util.Log
import com.nemo.cineman.entity.Account
import javax.inject.Inject

class UserRepository @Inject constructor (
    private val userService: UserService
){

    suspend fun getAccountDetail(sessionId: String) : Result<Account>{
        return try {
            val response = userService.getAccountDetail(sessionId)
            Log.d("MyLog", "Account: $response")
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)

        }
    }
}