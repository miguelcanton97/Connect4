package com.miguelcanton.conecta4.domain


class Game {
    private val board = mutableMapOf<Int, Chip>()

    companion object{
        const val NUM_ROWS = 7
        const val NUM_COLUMNS = 6
    }
}