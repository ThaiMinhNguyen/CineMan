package com.nemo.cineman.api

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nemo.cineman.entity.Account
import com.nemo.cineman.entity.AccountStateResponse
import com.nemo.cineman.entity.FavouriteBody
import com.nemo.cineman.entity.AccountResponse
import com.nemo.cineman.entity.ChangeItemRequest
import com.nemo.cineman.entity.ListMoviePagingSource
import com.nemo.cineman.entity.ListPagingSource
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieList
import com.nemo.cineman.entity.Rated
import com.nemo.cineman.entity.SharedPreferenceManager
import com.nemo.cineman.entity.UserMovieList
import com.nemo.cineman.entity.UserMovieListResponse
import com.nemo.cineman.entity.WatchlistBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor (
    private val userService: UserService,
    private val sharedPreferenceManager: SharedPreferenceManager
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

    suspend fun toggleMovieToFavourite(movieId: Int, favourite: Boolean) : Result<AccountResponse>{
        return try {
            val savedSessionId = sharedPreferenceManager.getSessionId()
            if (savedSessionId != null){
                val response = userService.toggleMovieToFavourite(savedSessionId, FavouriteBody("movie", movieId, favourite))
                Log.d("MyLog", "Add to favourite: $response")
                Result.success(response)
            } else {
                Result.failure(IllegalStateException("No valid session found"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun toggleSeriesToFavourite(movieId: Int, favourite: Boolean) : Result<AccountResponse>{
        return try {
            val savedSessionId = sharedPreferenceManager.getSessionId()
            if (savedSessionId != null){
                val response = userService.toggleMovieToFavourite(savedSessionId, FavouriteBody("tv", movieId, favourite))
                Log.d("MyLog", "Add to favourite: $response")
                Result.success(response)
            } else {
                Result.failure(IllegalStateException("No valid session found"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun toggleMovieToWatchlist(movieId: Int, watchlist: Boolean) : Result<AccountResponse>{
        return try {
            val savedSessionId = sharedPreferenceManager.getSessionId()
            if (savedSessionId != null){
                val response = userService.toggleMovieToWatchlist(savedSessionId, WatchlistBody("movie", movieId, watchlist))
                Log.d("MyLog", "Add to favourite: $response")
                Result.success(response)
            } else {
                Result.failure(IllegalStateException("No valid session found"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun toggleSeriesToWatchlist(movieId: Int, watchlist: Boolean) : Result<AccountResponse>{
        return try {
            val savedSessionId = sharedPreferenceManager.getSessionId()
            if (savedSessionId != null){
                val response = userService.toggleMovieToWatchlist(savedSessionId, WatchlistBody("tv", movieId, watchlist))
                Log.d("MyLog", "Add to favourite: $response")
                Result.success(response)
            } else {
                Result.failure(IllegalStateException("No valid session found"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun checkMovieFavourite(movieId: Int) : Result<AccountStateResponse>{
        return try {
            val savedSessionId = sharedPreferenceManager.getSessionId()
            val savedGuestSessionId = sharedPreferenceManager.getGuestSessionId()
            val response = if(savedSessionId != null) {
                userService.checkMovieFavourite(movieId, sessionId = savedSessionId, guestSessionId = null)
            } else {
                userService.checkMovieFavourite(movieId, sessionId = null, guestSessionId = savedGuestSessionId)
            }
            Log.d("MyLog", "Check favourite: $response")
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun addRateToMovie(movieId: Int, value: Double) : Result<AccountResponse>{
        return try {
            val savedSessionId = sharedPreferenceManager.getSessionId()
            val savedGuestSessionId = sharedPreferenceManager.getGuestSessionId()
            val rated = Rated(value)

            val response = if (savedSessionId != null) {
                userService.addRateToMovie(
                    movieId = movieId,
                    guestSessionId = null,
                    sessionId = savedSessionId,
                    rated = rated
                )
            } else if (savedGuestSessionId != null) {
                userService.addRateToMovie(
                    movieId = movieId,
                    guestSessionId = savedGuestSessionId,
                    sessionId = null,
                    rated = rated
                )
            } else {
                return Result.failure(IllegalStateException("No valid session found"))
            }
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }

    }

    fun getAccountList() : Flow<PagingData<MovieList>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { ListPagingSource(userService, sharedPreferenceManager) }
        ).flow
    }

    suspend fun createUserList(userMovieList: UserMovieList) : Result<UserMovieListResponse>{
        return try {
            val savedSessionId = sharedPreferenceManager.getSessionId()
            val response = if (savedSessionId != null){
                userService.createUserList(savedSessionId, userMovieList)
            } else {
                return Result.failure(IllegalStateException("No valid session found"))
            }
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun addMovieToList(listId: Int, movieId: Int) : Result<AccountResponse> {
        return try {
            val savedSessionId = sharedPreferenceManager.getSessionId()
            val response = if (savedSessionId != null){
                val addItemRequest = ChangeItemRequest(movieId)
                userService.addMovieToList(listId, savedSessionId, addItemRequest)
            } else {
                return Result.failure(IllegalStateException("No valid session found"))
            }
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getListName(listId: Int) : Result<String>{
        return try {
            val response = userService.getMoviesFromList(listId)
            return Result.success(response.name)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
   
    fun getMoviesFromList(listId: Int, language: String = "en-US", sortBy: String? = null): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 100
            ),
            pagingSourceFactory = { 
                ListMoviePagingSource(
                    userService = userService,
                    listId = listId,
                    language = language,
                    sortBy = sortBy
                ) 
            }
        ).flow
    }
    
}