package com.exemple.ticktacktoe

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.widget.Button

import androidx.core.content.ContextCompat
import androidx.core.util.TypedValueCompat.dpToPx

import com.exemple.ticktacktoe.databinding.FragmentSuperTicTacToeBinding

class GameBoard {

    private var arrSimple: MutableList<Int> = MutableList(9) { 0 }
    private var winListSuper: MutableList<Int> = MutableList(9) { 0 }
    private var arrSuper: MutableList<MutableList<Int>> = MutableList(9) { MutableList(9) { 0 } }



    fun setBackgroundButtonsSuper(context: Context, button: Button) {
        button.setBackgroundResource(R.drawable.button_background_tic_tac_toe)

        val params = button.layoutParams
        params.width = dpToPx(context, 30)
        params.height = dpToPx(context, 30)
        button.layoutParams = params

        button.setTextColor(ContextCompat.getColor(context, R.color.black))
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, spToPx(context, 20f))
        button.setTypeface(null, android.graphics.Typeface.BOLD)
    }
    fun setBackgroundButtons(context: Context, button: Button){
        button.setBackgroundResource(R.drawable.button_background_tic_tac_toe_simple)

        val params = button.layoutParams
        params.width = 100.dpToPx()
        params.height = 100.dpToPx()
        button.layoutParams = params

        button.setTextColor(ContextCompat.getColor(context, R.color.black))
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, spToPx(context, 75f))
        button.setTypeface(null, android.graphics.Typeface.BOLD)
    }

fun setStrokeOnButtonGreen(button: Button, context: Context) {
    val background = button.background as? GradientDrawable
    if (background != null) {
        // Змінюємо ширину обведення (у пікселях)
        val strokeWidth = context.resources.getDimensionPixelSize(R.dimen.new_stroke_width)
        val strokeColor = ContextCompat.getColor(context, R.color.green)
        background.setStroke(strokeWidth, strokeColor)
    }
}
    fun setStrokeOnButtonBlack(button: Button, context: Context) {
    val background = button.background as? GradientDrawable
    if (background != null) {
        val strokeWidth = context.resources.getDimensionPixelSize(R.dimen.new_stroke_width)
        val strokeColor = ContextCompat.getColor(context, R.color.black)
        background.setStroke(strokeWidth, strokeColor)
    }
}


    fun spToPx(context: Context, sp: Float): Float {
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        return sp * scaledDensity
    }
    private fun Int.dpToPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }
    private fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
    fun getBoardState(): MutableList<Int> {
        return arrSimple
    }

    fun getBoardSuper(): MutableList<MutableList<Int>> {
        return arrSuper
    }
    fun getWinListSuper() : MutableList<Int>{
        return winListSuper
    }

    fun updateBoardSimple(index: Int, player: Int) {
        arrSimple[index] = player
    }

    fun checkWinSuper(boardState: MutableList<MutableList<Int>>, binding: FragmentSuperTicTacToeBinding){
        boardState.forEachIndexed { indexI, i ->
            when {
                checkWin(1, i) -> winListSuper[indexI] = 1
                checkWin(2, i) -> winListSuper[indexI] = 2
                isDraw(i) -> winListSuper[indexI] = 3
            }
            when {
                checkWin(1, winListSuper) -> binding.TextWin.text = "Win X"
                checkWin(2, winListSuper) -> binding.TextWin.text = "Win O"
                isDraw(winListSuper) -> binding.TextWin.text = "Draw"
            }
        }
    }
    fun checkRightPlace(place : Int) : Boolean{
        return if(place == 10)
            false
        else
            winListSuper[place] == 1 || winListSuper[place] == 2 || winListSuper[place] == 3
    }

    fun updateBoardAllSimple(context: Context, array: MutableList<Int>, buttonArr: List<Button>) {

        array.forEachIndexed { index, element ->
            when (element) {
                1 -> buttonArr[index].text = "X"
                2 -> buttonArr[index].text = "O"
                else -> buttonArr[index].text = ""
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

    fun checkWinSuper(player: Int, array: MutableList<Int>): Boolean {
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

