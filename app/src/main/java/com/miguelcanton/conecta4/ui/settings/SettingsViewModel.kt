package com.miguelcanton.conecta4.ui.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    data class UiState(
        val homeNavigation: Boolean = false,
        val darkModeAnimation: Boolean = false,
        val darkMode: Boolean = false
    )

    fun onNavigateToHomeScreen() {
        _state.update { it.copy(homeNavigation = true) }
    }

    fun onNavigateToHomeScreenCompleted() {
        _state.update { it.copy(homeNavigation = false) }
    }

    fun onToggleDarkMode() {
        _state.update { it.copy(
            darkModeAnimation = true,
            darkMode = !state.value.darkMode
            ) }
    }

    fun onAnimationFinished() {
        _state.update { it.copy(darkModeAnimation = false) }
    }
}