package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.BuildConfig

class ApiKeyManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("torah_ia_settings", Context.MODE_PRIVATE)

    fun getCustomApiKey(): String {
        return prefs.getString(KEY_GEMINI_API_KEY, "") ?: ""
    }

    fun setCustomApiKey(key: String) {
        prefs.edit().putString(KEY_GEMINI_API_KEY, key.trim()).apply()
    }

    fun getEffectiveApiKey(): String {
        val customKey = getCustomApiKey()
        if (customKey.isNotBlank()) {
            return customKey
        }
        val buildKey = try { BuildConfig.GEMINI_API_KEY } catch (e: Exception) { "" }
        if (buildKey.isNotBlank() && buildKey != "MY_GEMINI_API_KEY") {
            return buildKey
        }
        return ""
    }

    fun isUsingCustomKey(): Boolean {
        return getCustomApiKey().isNotBlank()
    }

    fun hasKey(): Boolean {
        return getEffectiveApiKey().isNotBlank()
    }

    companion object {
        private const val KEY_GEMINI_API_KEY = "gemini_api_key_custom"
    }
}
