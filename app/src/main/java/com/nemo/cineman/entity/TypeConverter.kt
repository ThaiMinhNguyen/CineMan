package com.nemo.cineman.entity

import androidx.room.TypeConverter

class TypeConverter {
    @TypeConverter
    fun fromListIntToString(intList: List<Int>): String {
        return intList.toString()
    }

    @TypeConverter
    fun toListIntFromString(stringList: String): List<Int> {
        return stringList
            .removeSurrounding("[", "]")
            .split(",")
            .mapNotNull {
                it.trim().toIntOrNull()
            }
    }
}