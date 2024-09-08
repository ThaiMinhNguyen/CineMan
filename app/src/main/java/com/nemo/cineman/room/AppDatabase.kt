package com.nemo.cineman.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieDao

@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}