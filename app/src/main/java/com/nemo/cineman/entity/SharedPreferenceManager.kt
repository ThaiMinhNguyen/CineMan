package com.nemo.cineman.entity

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Named

class SharedPreferenceManager @Inject constructor(
    @Named("session_pref") private val prefs : SharedPreferences
) {

    companion object {
        private const val KEY_SESSION_ID = "session_id"
        private const val KEY_EXPIRED_TIME = "expired_time"
        private const val KEY_REQUEST_TOKEN = "request_token"
    }

    fun saveToken(requestToken : String){
        prefs.edit().apply {
            putString(KEY_REQUEST_TOKEN, requestToken)
            apply()
        }
    }

    fun getRequestToken() : String? {
        return prefs.getString(KEY_REQUEST_TOKEN, null)
    }

    // Lưu session và thời gian hết hạn
    fun saveSession(sessionId: String) {
//        val expiredTime = System.currentTimeMillis() + (10 * 24 * 60 * 60 * 1000)
        val expiredTime = System.currentTimeMillis() + (1000)

        prefs.edit().apply {
            putString(KEY_SESSION_ID, sessionId)
            putLong(KEY_EXPIRED_TIME, expiredTime)
            apply()
        }
    }

    // Lấy session ID
    fun getSessionId(): String? {
        return prefs.getString(KEY_SESSION_ID, null)
    }

    // Lấy thời gian hết hạn
    fun getExpiredTime(): Long {
        return prefs.getLong(KEY_EXPIRED_TIME, 0L)
    }

    // Kiểm tra xem session đã hết hạn chưa
    fun isSessionExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val expiredTime = getExpiredTime()
        return currentTime >= expiredTime
    }

    // Xóa session
    fun clearSession() {
        prefs.edit().apply {
            remove(KEY_SESSION_ID)
            remove(KEY_EXPIRED_TIME)
            apply()
        }
    }

    fun clearAll() {
        prefs.edit().apply {
            remove(KEY_SESSION_ID)
            remove(KEY_EXPIRED_TIME)
            remove(KEY_REQUEST_TOKEN)
            apply()
        }
    }
}