package com.miguelcanton.conecta4.ui.settings

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelcanton.conecta4.data.respositories.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val preferencesRepository: PreferencesRepository) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    data class UiState(
        val homeNavigation: Boolean = false,
        val darkModeAnimation: Boolean = false,
        val darkMode: Boolean = false
    )

    init{
        viewModelScope.launch {
            val darkMode = preferencesRepository.getDarkMode()
            _state.value = UiState(darkMode = darkMode)
        }
    }

    fun onNavigateToHomeScreen() {
        _state.update { it.copy(homeNavigation = true) }
    }

    fun onNavigateToHomeScreenCompleted() {
        _state.update { it.copy(homeNavigation = false) }
    }

    fun onToggleDarkMode() {
        _state.update { it.copy(darkModeAnimation = true) }
    }

    fun onAnimationFinished() {
        _state.update { it.copy(darkModeAnimation = false, darkMode = !state.value.darkMode) }
        viewModelScope.launch {
            preferencesRepository.toggleDarkMode()
        }
    }


}