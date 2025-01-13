package com.nemo.cineman.api

import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieCertification
import com.nemo.cineman.entity.MovieResponse
import com.nemo.cineman.entity.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    //======Movie List=======

    @Headers(
        "accept: application/json"
    )
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int? = 1
    ): MovieResponse


    @Headers(
        "accept: application/json"
    )
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int? = 1
    ): MovieResponse


    @Headers(
        "accept: application/json"
    )
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language:  String? = "en-US",
        @Query("page") page: Int? = 1
    ): MovieResponse


    @Headers(
        "accept: application/json"
    )
    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int? = 1
    ): MovieResponse

    //=========================================

    //==============Movie Detail===============

    @Headers(
        "accept: application/json"
    )
    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): Call<Movie>


    @Headers(
        "accept: application/json"
    )
    @GET("movie/{movie_id}/release_dates")
    fun getMovieCert(
        @Path("movie_id") movieId: Int
    ): Call<MovieCertification>

    @Headers(
        "accept: application/json"
    )
    @GET("movie/{movie_id}/similar")
    fun getSimilarMovie(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ) : Call<MovieResponse>


    @Headers(
        "accept: application/json"
    )
    @GET("movie/{movie_id}/videos")
    fun getMovieTrailer(
        @Path("movie_id") movieId: Int
    ) : Call<VideoResponse>


}