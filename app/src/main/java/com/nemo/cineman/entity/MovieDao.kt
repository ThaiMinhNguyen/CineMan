package com.nemo.cineman.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import javax.inject.Inject

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovie() : List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies : List<Movie>)
}