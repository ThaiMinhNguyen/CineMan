package com.nemo.cineman.entity

import androidx.room.Dao
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovie()
}