package com.miguelcanton.conecta4.domain

class Board {
    companion object {
        val ROW_LIST = listOf(Rows.ROW0, Rows.ROW1, Rows.ROW2, Rows.ROW3, Rows.ROW4, Rows.ROW5)
        val COLUMN_LIST = listOf(
            Columns.COLUMN0,
            Columns.COLUMN1,
            Columns.COLUMN2,
            Columns.COLUMN3,
            Columns.COLUMN4,
            Columns.COLUMN5,
            Columns.COLUMN6
        )
        val RIGHT_DIAGONAL_LIST = listOf(
            RightDiagonals.DIAGONAL_RIGHT1,
            RightDiagonals.DIAGONAL_RIGHT2,
            RightDiagonals.DIAGONAL_RIGHT3,
            RightDiagonals.DIAGONAL_RIGHT4,
            RightDiagonals.DIAGONAL_RIGHT5,
            RightDiagonals.DIAGONAL_RIGHT6
        )
        val LEFT_DIAGONAL_LIST = listOf(
            LeftDiagonals.DIAGONAL_LEFT1,
            LeftDiagonals.DIAGONAL_LEFT2,
            LeftDiagonals.DIAGONAL_LEFT3,
            LeftDiagonals.DIAGONAL_LEFT4,
            LeftDiagonals.DIAGONAL_LEFT5,
            LeftDiagonals.DIAGONAL_LEFT6
        )
    }
}