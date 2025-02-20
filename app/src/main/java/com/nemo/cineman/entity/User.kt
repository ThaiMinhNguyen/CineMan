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