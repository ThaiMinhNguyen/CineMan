package com.nemo.cineman.entity

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class Account(
    @SerializedName("avatar") val avatar: Avatar,
    @SerializedName("id") val id: Int,
    @SerializedName("iso_639_1") val language: String,
    @SerializedName("iso_3166_1") val country: String,
    @SerializedName("name") val name: String,
    @SerializedName("include_adult") val includeAdult: Boolean,
    @SerializedName("username") val username: String
)

data class Avatar(
    @SerializedName("gravatar") val gravatar: Gravatar,
    @SerializedName("tmdb") val tmdb: TmdbAvatar
)

data class Gravatar(
    @SerializedName("hash") val hash: String
)

data class TmdbAvatar(
    @SerializedName("avatar_path") val avatarPath: String?
)


data class FavouriteBody(
    @SerializedName("media_type") val mediaType : String,
    @SerializedName("media_id") val mediaId : Int,
    @SerializedName("favorite") val favourite : Boolean,
)

data class WatchlistBody(
    @SerializedName("media_type") val mediaType : String,
    @SerializedName("media_id") val mediaId : Int,
    @SerializedName("watchlist") val watchlist : Boolean,
)

data class AccountResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String,
)

data class AccountStateResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("favorite") val favorite: Boolean,
    @JsonAdapter(RatedAdapter::class)
    @SerializedName("rated") val rated: Rated?,
    @SerializedName("watchlist") val watchlist: Boolean
)

data class Rated(
    @SerializedName("value") val value: Double
)

data class MovieListResponse(
    val page: Int,
    val results: List<MovieList>,
    @SerializedName("total_pages") val totalPage: Int,
    @SerializedName("total_results") val totalResult: Int
)

data class MovieList(
    val description: String,
    val favorite_count: Int,
    val id: Int,
    val item_count: Int,
    val iso_639_1: String,
    val list_type: String,
    val name: String,
    val poster_path: String?
)

class RatedAdapter : JsonDeserializer<Rated?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Rated? {
        return when {
            json == null || json.isJsonNull -> null
            json.isJsonPrimitive && json.asJsonPrimitive.isBoolean -> null
            json.isJsonObject -> context?.deserialize(json, Rated::class.java)
            else -> null
        }
    }
}

data class UserMovieList(
    val name: String,
    val description: String,
    val language: String
)

data class UserMovieListResponse(
    val status_message: String,
    val success: Boolean,
    val status_code: Int,
    val list_id: Int
)

data class ChangeItemRequest(
    @SerializedName("media_id") val mediaId: Int
)

data class UserDetailListResponse(
    val created_by: String,
    val description: String,
    val favorite_count: Int,
    val id: String,
    val items: List<Movie>,
    val name: String,
    val page: Int = 1,
    @SerializedName("total_pages") val total_pages: Int = 1,
    @SerializedName("total_results") val total_results: Int = 0
)