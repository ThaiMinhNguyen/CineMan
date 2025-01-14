package com.nemo.cineman.entity

import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Named

class SharedPreferenceManager @Inject constructor(
    @Named("session_pref") private val prefs : SharedPreferences
) {

    companion object {
        private const val KEY_SESSION_ID = "session_id"
        private const val KEY_GUEST_SESSION_ID = "guest_session_id"
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
        val expiredTime = System.currentTimeMillis() + (10 * 24 * 60 * 60 * 1000)
        prefs.edit().apply {
            putString(KEY_SESSION_ID, sessionId)
            putLong(KEY_EXPIRED_TIME, expiredTime)
            apply()
        }
    }

    fun saveGuestSession(sessionId: String, expiresAt: String) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.ENGLISH)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        try {

            val date = dateFormat.parse(expiresAt)


            val expiredTime = date?.time ?: System.currentTimeMillis()

            prefs.edit().apply {
                putString(KEY_GUEST_SESSION_ID, sessionId)
                putLong(KEY_EXPIRED_TIME, expiredTime)
                apply()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            prefs.edit().apply {
                putString(KEY_GUEST_SESSION_ID, sessionId)
                putLong(KEY_EXPIRED_TIME, System.currentTimeMillis())
                apply()
            }
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

    fun getGuestSessionId(): String? {
        return prefs.getString(KEY_GUEST_SESSION_ID, null)
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
            remove(KEY_GUEST_SESSION_ID)
            remove(KEY_SESSION_ID)
            remove(KEY_EXPIRED_TIME)
            remove(KEY_REQUEST_TOKEN)
            apply()
        }
    }
}