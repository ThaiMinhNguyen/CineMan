package com.nemo.cineman.api

import com.nemo.cineman.entity.Account
import com.nemo.cineman.entity.AccountStateResponse
import com.nemo.cineman.entity.FavouriteBody
import com.nemo.cineman.entity.AccountResponse
import com.nemo.cineman.entity.Rated
import com.nemo.cineman.entity.VideoResponse
import com.nemo.cineman.entity.WatchlistBody
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
    @POST("account/account_id/favorite")
    suspend fun toggleMovieToFavourite(
        @Query("session_id") sessionId: String,
        @Body requestBody : FavouriteBody
    ) : AccountResponse


    @Headers(
        "accept: application/json"
    )
    @GET("movie/{movie_id}/account_states")
    suspend fun checkMovieFavourite(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId : String?,
        @Query("guest_session_id") guestSessionId: String?,
    ) : AccountStateResponse

    @Headers(
        "accept: application/json"
    )
    @POST("account/account_id/watchlist")
    suspend fun toggleMovieToWatchlist(
        @Query("session_id") sessionId: String,
        @Body requestBody : WatchlistBody
    ) : AccountResponse

    @Headers(
        "accept: application/json"
    )
    @GET("account/account_id/favorite/movies")
    suspend fun getAllFavouriteMovie(
        @Query("language") language: String? = "en-Us",
        @Query("page") page: Int? = 1,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String? = "created_at.asc"
    ) : VideoResponse

    @Headers(
        "accept: application/json",
        "Content-Type: application/json;charset=utf-8"
    )
    @POST("movie/{movie_id}/rating")
    suspend fun addRateToMovie(
        @Path("movie_id") movieId: Int,
        @Query("guest_session_id") guestSessionId: String?,
        @Query("session_id") sessionId : String?,
        @Body rated: Rated
    ) : AccountResponse

}