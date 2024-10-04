package com.nemo.cineman.entity

import android.os.Parcelable
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable


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



data class MovieCertification(
    val id: Int,
    val results: List<CertificationResult>
)

data class CertificationResult(
    val iso_3166_1: String,
    val release_dates: List<ReleaseDate>
)

data class ReleaseDate(
    val certification: String,
    val descriptors: List<String>,
    val iso_639_1: String,
    val note: String,
    val release_date: String,
    val type: Int
)