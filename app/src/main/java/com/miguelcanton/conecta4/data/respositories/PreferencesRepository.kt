package com.miguelcanton.conecta4.data.respositories

import com.miguelcanton.conecta4.data.sharedpreferences.SharedPreferencesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreferencesRepository @Inject constructor(private val sharedPreferencesApi: SharedPreferencesApi) {

    suspend fun getDarkMode(): Boolean {
        return withContext(Dispatchers.IO) {
            sharedPreferencesApi.getDarkMode()
        }
    }

    suspend fun setDarkMode(darkMode: Boolean) {
        withContext(Dispatchers.IO) {
            sharedPreferencesApi.setDarkMode(darkMode)
        }
    }

    suspend fun toggleDarkMode() {
        withContext(Dispatchers.IO) {
            sharedPreferencesApi.toggleDarkMode()
        }
    }
}