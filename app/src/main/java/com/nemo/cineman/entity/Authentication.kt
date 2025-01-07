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

data class SessionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("session_id") val sessionId: String? = null,
    @SerializedName("failure") val failure: Boolean? = null,
    @SerializedName("status_code") val statusCode: Int? = null,
    @SerializedName("status_message") val statusMessage: String? = null
)