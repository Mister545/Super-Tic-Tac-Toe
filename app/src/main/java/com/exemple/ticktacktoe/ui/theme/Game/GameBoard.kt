package com.exemple.ticktacktoe

class GameBoard {
    private var xOrO: Boolean = true
    private var arr: MutableList<Int> = MutableList(9) { 0 }
    fun getCurrentPlayer(): Boolean {
        return xOrO
    }

    fun switchPlayer() {
        xOrO = !xOrO
    }

    fun getBoardState(): MutableList<Int> {
        return arr
    }

    fun updateBoard(index: Int, player: Int) {
        arr[index] = player
    }

    fun resetBoard() {
        arr.fill(0)
    }

    fun checkWin(player: Int): Boolean {
        val winConditions = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
        )
        return winConditions.any { condition ->
            condition.all { arr[it] == player }
        }
    }

    fun isDraw(): Boolean {
        return arr.all { it != 0 }
    }
}
