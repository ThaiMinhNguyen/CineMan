package com.nemo.cineman.entity

import com.google.gson.annotations.SerializedName

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

data class FavouriteResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: Int,
)

data class AccountStateResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("favorite") val favorite: Boolean,
    @SerializedName("rated") val rated: Boolean,
    @SerializedName("watchlist") val watchlist: Boolean
)