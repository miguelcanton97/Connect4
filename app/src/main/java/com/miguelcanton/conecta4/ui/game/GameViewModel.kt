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
        (0 until NUM_ROWS * NUM_COLUMNS).forEach {
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
        val addNewChip: Boolean = false,
        val chipsWinIndex: List<Int> = emptyList<Int>().toMutableList(),
        val boardEnabled: Boolean = true
    )

    fun onRestartGame() {
        if (state.value.boardEnabled) {
            _state.update {
                it.copy(
                    cleanBoard = true,
                    playerTurn = _state.value.starterPlayer
                )
            }
            cleanBoard()
        }
    }

    private fun cleanBoard() {
        for ((index, _) in board) {
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
        if (state.value.boardEnabled) {
            _state.update {
                it.copy(
                    player1Wins = 0,
                    player2Wins = 0,
                    cleanBoard = true,
                    playerTurn = Players.PLAYER1,
                    starterPlayer = Players.PLAYER1
                )
            }
            cleanBoard()
        }
    }


    private fun changeStarterPlayer() {
        when (state.value.starterPlayer) {
            Players.PLAYER1 -> _state.update { it.copy(starterPlayer = Players.PLAYER2) }
            Players.PLAYER2 -> _state.update { it.copy(starterPlayer = Players.PLAYER1) }
        }
    }

    private fun checkIfWin(): Boolean {

        val chipPlayer = when (state.value.playerTurn) {
            Players.PLAYER1 -> Chip.PLAYER1
            Players.PLAYER2 -> Chip.PLAYER2
        }

        //Check rows
        for (row in rows) {
            var win = 0
            val chipsWinIndex = emptyList<Int>().toMutableList()
            for (index in row) {
                if (board[index] == chipPlayer) {
                    chipsWinIndex += index
                    win++
                    if (win == 4) {
                        addWin()
                        _state.update { it.copy(boardEnabled = false, chipsWinIndex = chipsWinIndex) }
                        return true
                    }
                } else {
                    chipsWinIndex.removeAll(0 until win)
                    win = 0
                }
            }
        }

        //Check columns
        for (column in columns) {
            var win = 0
            val chipsWinIndex = emptyList<Int>().toMutableList()
            for (index in column) {
                if (board[index] == chipPlayer) {
                    chipsWinIndex += index
                    win++
                    if (win == 4) {
                        addWin()
                        _state.update { it.copy(boardEnabled = false, chipsWinIndex = chipsWinIndex) }
                        return true
                    }
                } else {
                    chipsWinIndex.removeAll(0 until win)
                    win = 0
                }
            }
        }

        //Check rigth diagonals
        for (diaognal in rightDiagonals) {
            var win = 0
            val chipsWinIndex = emptyList<Int>().toMutableList()
            for (index in diaognal) {
                if (board[index] == chipPlayer) {
                    chipsWinIndex += index
                    win++
                    if (win == 4) {
                        addWin()
                        _state.update { it.copy(boardEnabled = false, chipsWinIndex = chipsWinIndex) }
                        return true
                    }
                } else {
                    chipsWinIndex.removeAll(0 until win)
                    win = 0
                }
            }
        }

        //Check left diagonals
        for (diaognal in leftDiagonals) {
            var win = 0
            val chipsWinIndex = emptyList<Int>().toMutableList()
            for (index in diaognal) {
                if (board[index] == chipPlayer) {
                    chipsWinIndex += index
                    win++
                    if (win == 4) {
                        addWin()
                        _state.update { it.copy(boardEnabled = false, chipsWinIndex = chipsWinIndex) }
                        return true
                    }
                } else {
                    chipsWinIndex.removeAll(0 until win)
                    win = 0
                }
            }
        }

        return false
    }

    private fun addWin() {
        when (state.value.playerTurn) {
            Players.PLAYER1 -> _state.update { it.copy(player1Wins = (state.value.player1Wins + 1)) }
            Players.PLAYER2 -> _state.update { it.copy(player2Wins = (state.value.player2Wins + 1)) }
        }
    }

    companion object {
        private const val NUM_ROWS = 7
        private const val NUM_COLUMNS = 6

        private val COLUMN0 = 0..35 step 7
        private val COLUMN1 = 1..36 step 7
        private val COLUMN2 = 2..37 step 7
        private val COLUMN3 = 3..38 step 7
        private val COLUMN4 = 4..39 step 7
        private val COLUMN5 = 5..40 step 7
        private val COLUMN6 = 6..41 step 7

        private val columns = listOf(COLUMN0, COLUMN1, COLUMN2, COLUMN3, COLUMN4, COLUMN5, COLUMN6)

        private val ROW0 = 0..6
        private val ROW1 = 7..13
        private val ROW2 = 14..20
        private val ROW3 = 21..27
        private val ROW4 = 28..34
        private val ROW5 = 35..41

        private val rows = listOf(ROW0, ROW1, ROW2, ROW3, ROW4, ROW5)

        private val DIAGONAL_RIGHT1 = 3..21 step 6
        private val DIAGONAL_RIGHT2 = 4..28 step 6
        private val DIAGONAL_RIGHT3 = 5..35 step 6
        private val DIAGONAL_RIGHT4 = 6..36 step 6
        private val DIAGONAL_RIGHT5 = 13..37 step 6
        private val DIAGONAL_RIGHT6 = 20..38 step 6

        private val rightDiagonals = listOf(DIAGONAL_RIGHT1, DIAGONAL_RIGHT2, DIAGONAL_RIGHT3, DIAGONAL_RIGHT4, DIAGONAL_RIGHT5, DIAGONAL_RIGHT6)

        private val DIAGONAL_LEFT1 = 14..38 step 8
        private val DIAGONAL_LEFT2 = 7..39 step 8
        private val DIAGONAL_LEFT3 = 0..40 step 8
        private val DIAGONAL_LEFT4 = 1..41 step 8
        private val DIAGONAL_LEFT5 = 2..34 step 8
        private val DIAGONAL_LEFT6 = 3..27 step 8

        private val leftDiagonals = listOf(DIAGONAL_LEFT1, DIAGONAL_LEFT2, DIAGONAL_LEFT3, DIAGONAL_LEFT4, DIAGONAL_LEFT5, DIAGONAL_LEFT6)
    }

    fun chipClicked(index: Int) {
        if (state.value.boardEnabled) {
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

                    if (!checkIfWin()) changeTurn()

                    break
                }
            }

        } else {
            _state.update { it.copy(showError = true) }
        }
    }

    private fun assignStarterPlayer() {
        _state.update { it.copy(playerTurn = state.value.starterPlayer) }
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

    fun chipsWinShowed() {
        changeStarterPlayer()
        assignStarterPlayer()
        cleanBoard()
        cleanWinList()
        enableBoard()
    }

    private fun enableBoard() {
        _state.update { it.copy(boardEnabled = true) }
    }

    private fun cleanWinList() {
        _state.update { it.copy(chipsWinIndex = emptyList<Int>().toMutableList()) }
    }
}