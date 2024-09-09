package com.nemo.cineman.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nemo.cineman.entity.TypeConverter
import androidx.room.TypeConverters
import com.nemo.cineman.entity.Movie
import com.nemo.cineman.entity.MovieDao

@TypeConverters(TypeConverter::class)
@Database(entities = [Movie::class], exportSchema = false ,version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}