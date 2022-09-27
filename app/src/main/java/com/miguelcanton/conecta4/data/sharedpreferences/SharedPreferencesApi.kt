package com.miguelcanton.conecta4.data.sharedpreferences

import android.content.SharedPreferences
import com.miguelcanton.conecta4.data.sharedpreferences.SharedPreferencesConstants.DARK_MODE
import javax.inject.Inject

class SharedPreferencesApi @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun setDarkMode(darkMode: Boolean) {
        sharedPreferences.edit()?.putBoolean(DARK_MODE, darkMode)?.apply()
    }

    fun getDarkMode(): Boolean {
        return sharedPreferences.getBoolean(DARK_MODE, false)
    }

    fun toggleDarkMode() {
        when (getDarkMode()) {
            true -> setDarkMode(false)
            false -> setDarkMode(true)
        }
    }
}