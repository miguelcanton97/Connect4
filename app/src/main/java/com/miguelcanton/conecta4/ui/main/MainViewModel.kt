package com.miguelcanton.conecta4.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    data class UiState(
        val navigation: Boolean = false
    )

    fun onNavigateToGameScreen(){
        _state.update { it.copy(navigation = true)}
    }

    fun onNavigateToGameScreenCompleted() {
        _state.update { it.copy(navigation = false)}
    }
}