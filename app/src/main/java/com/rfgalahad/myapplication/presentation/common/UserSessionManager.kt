package com.rfgalahad.myapplication.presentation.common

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserSessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "user_session"
        private const val KEY_USER_EMAIL = "user_email"
    }

    fun saveUserEmail(email: String) {
        prefs.edit { putString(KEY_USER_EMAIL, email) }
    }

    fun getUserEmail(): String? {
        return prefs.getString(KEY_USER_EMAIL, null)
    }

    fun clearSession() {
        prefs.edit { clear() }
    }
}