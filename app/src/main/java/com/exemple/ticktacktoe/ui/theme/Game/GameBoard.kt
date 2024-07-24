package com.exemple.ticktacktoe

import android.util.Log
import android.widget.Button
import com.exemple.ticktacktoe.databinding.ActivityMainBinding

class GameBoard {

    private var arrSimple: MutableList<Int> = MutableList(9) { 0 }
    private var arrSuper: MutableList<MutableList<Int>> = MutableList(9) { MutableList(9) { 0 } }


    fun getBoardState(): MutableList<Int> {
        return arrSimple
    }

    fun getBoardSuper(): MutableList<MutableList<Int>> {
        return arrSuper
    }

    fun updateBoardSimple(index: Int, player: Int) {
        arrSimple[index] = player
    }


    fun updateBoardAllSimple(array: MutableList<Int>, buttonArr: List<Button>) {

        array.forEachIndexed { index, element ->
            when (element) {
                1 -> buttonArr[index].setBackgroundResource(R.drawable.x)
                2 -> buttonArr[index].setBackgroundResource(R.drawable.o)
                else -> buttonArr[index].setBackgroundResource(R.drawable.white_background)
            }
        }
    }

    fun updateBoardAllSuper(array: MutableList<MutableList<Int>>, buttonArr: List<Button>) {

//        array.forEachIndexed { index, element ->
//            when (element) {
//                1 -> buttonArr[index].setBackgroundResource(R.drawable.x)
//                2 -> buttonArr[index].setBackgroundResource(R.drawable.o)
//                else -> buttonArr[index].setBackgroundResource(R.drawable.white_background)
//            }
//        }
    }


    fun resetBoardSimple() {
        arrSimple.fill(0)
    }
    fun resetBoardSuper() {
        for (i in arrSuper.indices) {
            for (j in arrSuper[i].indices) {
                arrSuper[i][j] = 0
            }
        }
    }

    fun checkWin(player: Int, array: MutableList<Int>): Boolean {
        val winConditions = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
            intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
        )
        return winConditions.any { condition ->
            condition.all { array[it] == player }
        }
    }

    fun isDraw(array: MutableList<Int>): Boolean {
        return array.all { it != 0 }
    }
}

