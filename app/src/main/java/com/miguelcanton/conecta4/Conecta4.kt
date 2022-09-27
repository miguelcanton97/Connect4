package com.miguelcanton.conecta4

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.miguelcanton.conecta4.data.respositories.PreferencesRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class Conecta4 : Application() {

    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()

        GlobalScope.launch{
            val darkMode = preferencesRepository.getDarkMode()
            if (darkMode)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
