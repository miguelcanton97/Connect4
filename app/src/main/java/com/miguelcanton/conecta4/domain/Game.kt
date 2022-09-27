package com.miguelcanton.conecta4.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Game @Inject constructor() {
    val board = mutableMapOf<Int, Chip>()

    companion object {
        const val NUM_ROWS = 7
        const val NUM_COLUMNS = 6
    }
}