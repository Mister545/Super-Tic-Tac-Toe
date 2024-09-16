package com.exemple.ticktacktoe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.exemple.ticktacktoe.databinding.ActivitySimpleTicTacToeBinding
import com.exemple.ticktacktoe.Game.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class OfflineSimple : AppCompatActivity() {



    private lateinit var binding: ActivitySimpleTicTacToeBinding
    private lateinit var buttonArr: List<Button>
    private val gameBoard = GameBoard()
    private var arrSimple: MutableList<Int> = MutableList(9) { 0 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleTicTacToeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bComeBackSimple.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            }
        removeFragment()
        initialization()
        }

    private fun initialization() {
        buttonArr = listOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )
        setupButtonListeners()
        resetGame()
    }
    private fun setupButtonListeners() {
        for ((index, button) in buttonArr.withIndex()) {
            button.setOnClickListener {
                updateBoard(index, button)
            }
        }
    }
    private fun updateBoard(index: Int, button: Button) {

            if (arrSimple[index] == 0) {
                val currentPlayer = if (getStep(arrSimple)) 1 else 2
                arrSimple[index] = currentPlayer
                button.text = (if (getStep(arrSimple)) "X" else "O")
                updateUI(arrSimple)
                win(gameBoard.checkWin(currentPlayer,arrSimple), currentPlayer)
        }
    }

    private fun win(isWin: Boolean, player: Int){
        val winner = if (player == 1) "X" else "O"
        if (isWin){
            binding.TicTacToeText.text = "Winner $winner"
        }
    }

    private fun updateUI(boardState: MutableList<Int>) {

        for ((index, button) in buttonArr.withIndex()) {
            when (boardState[index]) {
                1 -> button.text = "X"
                2 -> button.text = "O"
                else -> gameBoard.setBackgroundButtonsSimple(this, button)
            }
        }
    }


//    private fun handleWin(player: String, winCode: Int) {
//        Toast.makeText(this, "Win $player", Toast.LENGTH_LONG).show()
//        firebaseService.setWin(winCode)
//    }
//
//    private fun handleDraw() {
//        Toast.makeText(this, "DRAW", Toast.LENGTH_LONG).show()
//        firebaseService.setWin(0)
//    }

    private fun resetGame() {
        gameBoard.resetBoardSimple()
        buttonArr.forEach { gameBoard.setBackgroundButtonsSimple(this, it) }
    }

    private fun resetUi(){
        buttonArr.forEachIndexed { _, button ->
            button.text = ""
        }
        initialization()
    }
    private fun replaceFragment(fragment: DialogFragment) {
        if (!isFinishing && !supportFragmentManager.isDestroyed) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            if (!supportFragmentManager.isStateSaved) {
                fragmentTransaction.replace(binding.fragmentContainerView3.id, fragment)
                fragmentTransaction.commit()
            } else {
                fragmentTransaction.replace(binding.fragmentContainerView3.id, fragment)
                fragmentTransaction.commitAllowingStateLoss()
            }
        } else {
            println("Замінити фрагмент неможливо: активність знищена або завершується")
        }
    }

    private fun removeFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView3)
        if (fragment != null && !supportFragmentManager.isStateSaved) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }
    }
    fun getStep(list: MutableList<Int>) : Boolean {
        var x = 0
        var o = 0
        var y = 0

        for (value in list) {
            y += value
            if (value == 1) {
                x++
            } else if (value == 2) {
                o++
            }
        }
        val ret = when (y) {
            0 -> true
            3 -> true
            5 -> true
            6 -> true
            9 -> true
            12 -> true
            else -> false
        }
        return ret
    }
}
