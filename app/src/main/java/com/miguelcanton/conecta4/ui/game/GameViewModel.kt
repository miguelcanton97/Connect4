package com.miguelcanton.conecta4.ui.game

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {


    private val board = mutableMapOf<Int, Chip>()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        (0 until ROWS * COLUMNS).forEach {
            board[it] = Chip.EMPTY
        }
    }

    enum class Players {
        PLAYER1,
        PLAYER2
    }

    enum class Chip {
        EMPTY,
        PLAYER1,
        PLAYER2
    }

    data class UiState(
        val playerTurn: Players = Players.PLAYER1,
        val starterPlayer: Players = Players.PLAYER1,
        val navigateToHome: Boolean = false,
        val restartGame: Boolean = false,
        val gameCompleted: Boolean = false,
        val cleanBoard: Boolean = false,
        val player1Wins: Int = 0,
        val player2Wins: Int = 0,
        val showError: Boolean = false,
        val newChip: Pair<Int, Players> = Pair(0, Players.PLAYER1),
        val addNewChip: Boolean = false
    )

    fun onRestartGame() {
        _state.update {
            it.copy(
                cleanBoard = true,
                playerTurn = _state.value.starterPlayer
            )
        }
        cleanBoard()
    }

    private fun cleanBoard() {
        for ((index,_) in board){
            board[index] = Chip.EMPTY
        }
    }

    fun gameRestarted() {
        _state.update { it.copy(cleanBoard = false) }
    }

    fun onNavigateToHome() {
        _state.update { it.copy(navigateToHome = true) }
    }

    fun navigatedToHomeCompleted() {
        _state.update { it.copy(navigateToHome = false) }
    }

    fun onCleanScore() {
        _state.update {
            it.copy(
                player1Wins = 0,
                player2Wins = 0,
                cleanBoard = true
            )
        }
    }

    fun onScoreCleaned() {
    }

    fun gameCompleted() {
        changeStarterPlayer()
        TODO("Not yet implemented")
    }

    private fun changeStarterPlayer() {
        when (state.value.starterPlayer) {
            Players.PLAYER1 -> _state.update { it.copy(starterPlayer = Players.PLAYER2) }
            Players.PLAYER2 -> _state.update { it.copy(starterPlayer = Players.PLAYER1) }
        }
    }

    fun checkIfWin() {
        TODO("Not yet implemented")
    }

    companion object {
        private const val ROWS = 7
        private const val COLUMNS = 6

        private val COLUMN0 = 0..35 step 7
        private val COLUMN1 = 1..36 step 7
        private val COLUMN2 = 2..37 step 7
        private val COLUMN3 = 3..38 step 7
        private val COLUMN4 = 4..39 step 7
        private val COLUMN5 = 5..40 step 7
        private val COLUMN6 = 6..41 step 7
    }

    fun chipClicked(index: Int) {
        when (index) {
            in COLUMN0 -> addChipIfPossible(0)
            in COLUMN1 -> addChipIfPossible(1)
            in COLUMN2 -> addChipIfPossible(2)
            in COLUMN3 -> addChipIfPossible(3)
            in COLUMN4 -> addChipIfPossible(4)
            in COLUMN5 -> addChipIfPossible(5)
            in COLUMN6 -> addChipIfPossible(6)
        }
    }

    private fun addChipIfPossible(columnClicked: Int) {
        if (board[columnClicked] == Chip.EMPTY) {
            val lowerChip = columnClicked + 35
            val higherChip = columnClicked

            for (chipIndex in lowerChip downTo higherChip step 7) {
                if (board[chipIndex] == Chip.EMPTY) {

                    _state.update {
                        it.copy(
                            newChip = Pair(chipIndex, _state.value.playerTurn),
                            addNewChip = true
                        )
                    }

                    when (_state.value.playerTurn) {
                        Players.PLAYER1 -> board[chipIndex] = Chip.PLAYER1
                        Players.PLAYER2 -> board[chipIndex] = Chip.PLAYER2
                    }

                    changeTurn()
                    break
                }
            }

        } else {
            _state.update { it.copy(showError = true) }
        }
    }

    fun showedError() {
        _state.update { it.copy(showError = false) }
    }

    fun newChipAdded() {
        _state.update { it.copy(addNewChip = false) }
    }

    private fun changeTurn() {
        when (state.value.playerTurn) {
            Players.PLAYER1 -> _state.update { it.copy(playerTurn = Players.PLAYER2) }
            Players.PLAYER2 -> _state.update { it.copy(playerTurn = Players.PLAYER1) }
        }
    }

}