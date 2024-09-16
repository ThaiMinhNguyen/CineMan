package com.nemo.cineman.entity

import android.os.Parcelable
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable


//{
//    "adult": false,
//    "backdrop_path": "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
//    "genre_ids": [
//    28,
//    35,
//    878
//    ],
//    "id": 533535,
//    "original_language": "en",
//    "original_title": "Deadpool & Wolverine",
//    "overview": "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.",
//    "popularity": 3500.919,
//    "poster_path": "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
//    "release_date": "2024-07-24",
//    "title": "Deadpool & Wolverine",
//    "video": false,
//    "vote_average": 7.752,
//    "vote_count": 2692
//}

@Entity(tableName = "movies")
@Parcelize
data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    @PrimaryKey
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) : Parcelable


data class MovieResponse(
    val results: List<Movie>
)