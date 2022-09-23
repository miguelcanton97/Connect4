package com.miguelcanton.conecta4.ui.game

import androidx.lifecycle.ViewModel
import com.miguelcanton.conecta4.domain.*
import com.miguelcanton.conecta4.domain.Game.Companion.NUM_COLUMNS
import com.miguelcanton.conecta4.domain.Game.Companion.NUM_ROWS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val game: Game
) : ViewModel() {

    //private val board = mutableMapOf<Int, Chip>()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        (0 until NUM_ROWS * NUM_COLUMNS).forEach {
            game.board[it] = Chip.EMPTY
        }
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

    fun boardCleaned() {
        _state.update { it.copy(cleanBoard = false) }
    }

    private fun cleanBoard() {
        for ((index, _) in game.board) {
            game.board[index] = Chip.EMPTY
        }
    }

    fun onNavigateToHome() {
        _state.update { it.copy(navigateToHome = true) }
    }

    fun navigatedToHomeCompleted() {
        _state.update { it.copy(navigateToHome = false) }
        cleanBoard()
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

        if (checkIfRowWin(chipPlayer)) return true
        if (checkIfColumnWin(chipPlayer)) return true
        if (checkIfRightDiagonalWin(chipPlayer)) return true
        if (checkIfLeftDiagonalWin(chipPlayer)) return true

        return false
    }

    private fun addWin() {
        when (state.value.playerTurn) {
            Players.PLAYER1 -> _state.update { it.copy(player1Wins = (state.value.player1Wins + 1)) }
            Players.PLAYER2 -> _state.update { it.copy(player2Wins = (state.value.player2Wins + 1)) }
        }
    }

    fun chipClicked(index: Int) {
        if (state.value.boardEnabled) {
            when (index) {
                in Columns.COLUMN0 -> addChipIfPossible(0)
                in Columns.COLUMN1 -> addChipIfPossible(1)
                in Columns.COLUMN2 -> addChipIfPossible(2)
                in Columns.COLUMN3 -> addChipIfPossible(3)
                in Columns.COLUMN4 -> addChipIfPossible(4)
                in Columns.COLUMN5 -> addChipIfPossible(5)
                in Columns.COLUMN6 -> addChipIfPossible(6)
            }
        }
    }

    private fun addChipIfPossible(columnClicked: Int) {
        if (game.board[columnClicked] == Chip.EMPTY) {
            val lowerChip = columnClicked + 35
            val higherChip = columnClicked

            for (chipIndex in lowerChip downTo higherChip step 7) {
                if (game.board[chipIndex] == Chip.EMPTY) {

                    _state.update {
                        it.copy(
                            newChip = Pair(chipIndex, _state.value.playerTurn),
                            addNewChip = true
                        )
                    }

                    when (_state.value.playerTurn) {
                        Players.PLAYER1 -> game.board[chipIndex] = Chip.PLAYER1
                        Players.PLAYER2 -> game.board[chipIndex] = Chip.PLAYER2
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

    fun onNewGame() {
        cleanChipsWinList()
        _state.update { it.copy(cleanBoard = true) }
        changeStarterPlayer()
        assignStarterPlayer()
        cleanBoard()
        enableBoard()
    }

    private fun enableBoard() {
        _state.update { it.copy(boardEnabled = true) }
    }

    private fun cleanChipsWinList() {
        _state.update { it.copy(chipsWinIndex = emptyList<Int>().toMutableList()) }
    }

    private fun checkIfLeftDiagonalWin(chipPlayer: Chip): Boolean {
        for (diaognal in Board.LEFT_DIAGONAL_LIST) {
            var win = 0
            val chipsWinIndex = emptyList<Int>().toMutableList()
            for (index in diaognal) {
                if (game.board[index] == chipPlayer) {
                    chipsWinIndex += index
                    win++
                    if (win == 4) {
                        addWin()
                        _state.update {
                            it.copy(
                                boardEnabled = false,
                                chipsWinIndex = chipsWinIndex
                            )
                        }
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

    private fun checkIfRightDiagonalWin(chipPlayer: Chip): Boolean {
        for (diaognal in Board.RIGHT_DIAGONAL_LIST) {
            var win = 0
            val chipsWinIndex = emptyList<Int>().toMutableList()
            for (index in diaognal) {
                if (game.board[index] == chipPlayer) {
                    chipsWinIndex += index
                    win++
                    if (win == 4) {
                        addWin()
                        _state.update {
                            it.copy(
                                boardEnabled = false,
                                chipsWinIndex = chipsWinIndex
                            )
                        }
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

    private fun checkIfColumnWin(chipPlayer: Chip): Boolean {
        for (column in Board.COLUMN_LIST) {
            var win = 0
            val chipsWinIndex = emptyList<Int>().toMutableList()
            for (index in column) {
                if (game.board[index] == chipPlayer) {
                    chipsWinIndex += index
                    win++
                    if (win == 4) {
                        addWin()
                        _state.update {
                            it.copy(
                                boardEnabled = false,
                                chipsWinIndex = chipsWinIndex
                            )
                        }
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

    private fun checkIfRowWin(chipPlayer: Chip): Boolean {
        for (row in Board.ROW_LIST) {
            var win = 0
            val chipsWinIndex = emptyList<Int>().toMutableList()
            for (index in row) {
                if (game.board[index] == chipPlayer) {
                    chipsWinIndex += index
                    win++
                    if (win == 4) {
                        addWin()
                        _state.update {
                            it.copy(
                                boardEnabled = false,
                                chipsWinIndex = chipsWinIndex
                            )
                        }
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
}