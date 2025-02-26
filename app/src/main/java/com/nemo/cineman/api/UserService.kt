package com.nemo.cineman.api

import com.nemo.cineman.entity.Account
import com.nemo.cineman.entity.AccountStateResponse
import com.nemo.cineman.entity.FavouriteBody
import com.nemo.cineman.entity.FavouriteResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    //==============Account Related=============

    @Headers(
        "accept: application/json"
    )
    @GET("account/account_id")
    suspend fun getAccountDetail(
        @Query("session_id") sessionId: String
    ) : Account

    @Headers(
        "accept: application/json"
    )
    @POST("account/{account_id}/favorite")
    suspend fun toggleMovieToFavourite(
        @Path("account_id") accountId: Int,
        @Body requestBody : FavouriteBody
    ) : FavouriteResponse

//    https://api.themoviedb.org/3/movie/:movie_id/account_states?session_id=e26507544e6d60e8fc445d63d1b9773f01b38b8f

    @Headers(
        "accept: application/json"
    )
    @GET("movie/{movie_id}/account_states")
    suspend fun checkMovieFavourite(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String
    ) : AccountStateResponse
}