package com.nemo.cineman.api

import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieCertification
import com.nemo.cineman.entity.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    //======Movie List=======

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZGMyNjYxMGZmOWI1MmNmNTAxMzM2MzFlOGQ5YzlkOCIsIm5iZiI6MTcyNjA2MTA2My45NDE5Nywic3ViIjoiNjZkZGE3MTA0MGZiYzEwOThiMzhiZmViIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.hfH63RKuXrt8vdumBrFL7TRzXwPXAppHznTC9ZE5Sk0"
    )
    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieResponse>


    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZGMyNjYxMGZmOWI1MmNmNTAxMzM2MzFlOGQ5YzlkOCIsIm5iZiI6MTcyNjA2MTA2My45NDE5Nywic3ViIjoiNjZkZGE3MTA0MGZiYzEwOThiMzhiZmViIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.hfH63RKuXrt8vdumBrFL7TRzXwPXAppHznTC9ZE5Sk0"
    )
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieResponse>


    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZGMyNjYxMGZmOWI1MmNmNTAxMzM2MzFlOGQ5YzlkOCIsIm5iZiI6MTcyNjA2MTA2My45NDE5Nywic3ViIjoiNjZkZGE3MTA0MGZiYzEwOThiMzhiZmViIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.hfH63RKuXrt8vdumBrFL7TRzXwPXAppHznTC9ZE5Sk0"
    )
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieResponse>


    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZGMyNjYxMGZmOWI1MmNmNTAxMzM2MzFlOGQ5YzlkOCIsIm5iZiI6MTcyNjA2MTA2My45NDE5Nywic3ViIjoiNjZkZGE3MTA0MGZiYzEwOThiMzhiZmViIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.hfH63RKuXrt8vdumBrFL7TRzXwPXAppHznTC9ZE5Sk0"
    )
    @GET("movie/upcoming")
    fun getUpComingMovies(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<MovieResponse>

    //=========================================

    //==============Movie Detail===============

    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZGMyNjYxMGZmOWI1MmNmNTAxMzM2MzFlOGQ5YzlkOCIsIm5iZiI6MTcyNjA2MTA2My45NDE5Nywic3ViIjoiNjZkZGE3MTA0MGZiYzEwOThiMzhiZmViIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.hfH63RKuXrt8vdumBrFL7TRzXwPXAppHznTC9ZE5Sk0"
    )
    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): Call<Movie>


    @Headers(
        "accept: application/json",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZGMyNjYxMGZmOWI1MmNmNTAxMzM2MzFlOGQ5YzlkOCIsIm5iZiI6MTcyNjA2MTA2My45NDE5Nywic3ViIjoiNjZkZGE3MTA0MGZiYzEwOThiMzhiZmViIiwic2NvcGVzIjpbImFwaV9yZWFkIl0sInZlcnNpb24iOjF9.hfH63RKuXrt8vdumBrFL7TRzXwPXAppHznTC9ZE5Sk0"
    )
    @GET("/movie/{movie_id}/release_dates")
    fun getMovieCert(
        @Path("movie_id") movieId: Int
    ): Call<MovieCertification>

}