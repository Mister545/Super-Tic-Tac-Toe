// MainActivity.kt
package com.exemple.ticktacktoe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.exemple.ticktacktoe.databinding.ActivityMainBinding
import com.exemple.ticktacktoe.databinding.FragmentSimpleTicTacToeBinding
import com.exemple.ticktacktoe.databinding.FragmentSuperTicTacToeBinding
import com.exemple.ticktacktoe.ui.theme.Game.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class SimpleTicTacToe : AppCompatActivity() {

    private lateinit var binding: FragmentSimpleTicTacToeBinding
    private lateinit var buttonArr: List<Button>
    private val gameBoard = GameBoard()
    val firebaseService = FirebaseService()
    private val database = FirebaseDatabase.getInstance()
    private lateinit var firebaseListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSimpleTicTacToeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonArr = arrayListOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )
        initialization()


        binding.buttonReset.setOnClickListener {
            resetUi()
        }
        binding.bComeBackSimple.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val databaseReference = database.getReference("StatSimple")
        databaseReference.removeEventListener(firebaseListener)
    }

    private fun initialization() {
        setupButtonListeners()
        firebaseService.setStep(true)
        resetGame()
        setupFirebaseListener()  // Налаштування постійного слухача змін
    }
    private fun setupButtonListeners() {
        for ((index, button) in buttonArr.withIndex()) {
            button.setOnClickListener {
                updateBoard(index, button)
            }
        }
    }

    private fun updateBoard(index: Int, button: Button) {
        getBoardState { listBD, step ->
            if (listBD[index] == 0) {
                val currentPlayer = if (step) 1 else 2
                listBD[index] = currentPlayer
                firebaseService.setStep(!step)
                button.text = (if (step) "X" else "O")
                firebaseService.setBoardStateSimple(listBD)
            }
        }
    }


    private fun disableAllButtons() {
        buttonArr.forEach { it.isClickable = false }
    }
    private fun enableAllButtons() {
        buttonArr.forEach { it.isClickable = true }
    }

    private fun getBoardState(callback: (MutableList<Int>, Boolean) -> Unit) {
        val databaseReference = database.getReference("StatSimple/data")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<Int>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { 0 }
                val gameStatus = firebaseService.getStep(boardState)
                gameBoard.updateBoardAllSimple(this@SimpleTicTacToe, boardState, buttonArr)

                callback(boardState, gameStatus)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(MutableList(9) { 0 }, false)

                Log.e("ooo", "Error: ${error.message}")
            }
        })
    }

    private fun setupFirebaseListener() {
        val databaseReference = database.getReference("StatSimple/data")
        firebaseListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<Int>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { 0 }
                updateUI(boardState)
                when {
                    gameBoard.checkWin(1, boardState) -> binding.TicTacToeText.text = "Winner X"
                    gameBoard.checkWin(2, boardState) -> binding.TicTacToeText.text = "Winner O"
                    gameBoard.isDraw(boardState) -> binding.TicTacToeText.text = "Draw"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooo", "Error: ${error.message}")
            }
        }
        databaseReference.addValueEventListener(firebaseListener)
    }

    private fun updateUI(boardState: MutableList<Int>) {

            for ((index, button) in buttonArr.withIndex()) {
                when (boardState[index]) {
                    1 -> button.text = "X"
                    2 -> button.text = "O"
                    else -> gameBoard.setBackgroundButtons(this, button)
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
        firebaseService.setBoardStateSimple(gameBoard.getBoardState())
        buttonArr.forEach { gameBoard.setBackgroundButtons(this, it) }
    }

    private fun resetUi(){
        buttonArr.forEachIndexed { index, button ->
            button.text = ""
        }
        initialization()
    }
}
