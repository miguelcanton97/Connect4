package com.miguelcanton.conecta4.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    data class UiState(
        val gameNavigation: Boolean = false,
        val settingsNavigation: Boolean = false
    )

    fun onNavigateToGameScreen(){
        _state.update { it.copy(gameNavigation = true)}
    }

    fun onNavigateToGameScreenCompleted() {
        _state.update { it.copy(gameNavigation = false)}
    }

    fun onNavigateToSettingsScreen(){
        _state.update { it.copy(settingsNavigation = true)}
    }

    fun onNavigateToSettingsScreenCompleted() {
        _state.update { it.copy(settingsNavigation = false)}
    }
}