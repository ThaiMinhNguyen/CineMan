package com.nemo.cineman.entity

import com.google.gson.annotations.SerializedName

data class RequestTokenResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("expires_at") val expiresAt: String,
    @SerializedName("request_token") val requestToken: String
)

data class RequestTokenBody(
    @SerializedName("request_token") val requestToken: String
)

