package com.nemo.cineman.api

import android.util.Log
import com.nemo.cineman.entity.Account
import com.nemo.cineman.entity.AccountStateResponse
import com.nemo.cineman.entity.FavouriteBody
import com.nemo.cineman.entity.FavouriteResponse
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

    suspend fun toggleMovieToFavourite(accountId : Int, movieId: Int, favourite: Boolean) : Result<FavouriteResponse>{
        return try {
            val response = userService.toggleMovieToFavourite(accountId, FavouriteBody("movie", movieId, favourite))
            Log.d("MyLog", "Add to favourite: $response")
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun checkMovieFavourite(movieId: Int, sessionId: String) : Result<AccountStateResponse>{
        return try {
            val response = userService.checkMovieFavourite(movieId, sessionId)
            Log.d("MyLog", "Check favourite: $response")
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}