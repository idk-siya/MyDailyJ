package com.example.poefn.ui.login

import android.content.Context
import android.content.SharedPreferences

object SessionManager {

    private const val PREFS_NAME = "mydailyj_session"
    private const val KEY_TOKEN = "auth_token"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        if (!::prefs.isInitialized) {
            prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    fun saveToken(token: String) {
        prefs.edit().putString(KEY_TOKEN, token).apply()
    }

    fun getToken(): String? =
        if (::prefs.isInitialized) prefs.getString(KEY_TOKEN, null) else null

    fun clear() {
        if (::prefs.isInitialized) prefs.edit().remove(KEY_TOKEN).apply()
    }
}
