package com.nemo.cineman.api

import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.RequestTokenBody
import com.nemo.cineman.entity.RequestTokenResponse
import com.nemo.cineman.entity.SessionResponse
import com.nemo.cineman.entity.UsernamePasswordBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService
) {
    suspend fun getRequestToken() : Result<RequestTokenResponse?> {
        val response = authService.getRequestToken().awaitResponse()
        return try {
            if(response.isSuccessful){
                Result.success(response.body())
            } else{
                val error = response.errorBody()?.toString()
                Result.failure(Throwable(error))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun getNewSession(requestToken : String) : Result<SessionResponse?> {
        return try {
            val requestTokenBody : RequestTokenBody = RequestTokenBody(requestToken)
            val response = authService.getNewSession(requestTokenBody)
            Result.success(response)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun getNewSessionWithLogin(userName : String, password : String, requestToken : String) : Result<SessionResponse?> {
        return try {
            val usernamePasswordBody : UsernamePasswordBody = UsernamePasswordBody(userName, password, requestToken)
            val response = authService.getNewSessionWithLogin(usernamePasswordBody)
            Result.success(response)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}