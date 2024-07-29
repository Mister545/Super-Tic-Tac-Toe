// MainActivity.kt
package com.exemple.ticktacktoe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.exemple.ticktacktoe.databinding.ActivityMainBinding
import com.exemple.ticktacktoe.ui.theme.Game.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonArr: List<Button>
    private val gameBoard = GameBoard()
    val firebaseService = FirebaseService()
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonArr = arrayListOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )

        initialization()

        binding.bSuper.setOnClickListener {
            replaceFragment(SuperTicTacToe())
        }

        binding.buttonReset.setOnClickListener {
            initialization()
        }
    }

    private fun initialization() {
        setupButtonListeners()
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
                val currentPlayer = if (step) 2 else 1
                listBD[index] = currentPlayer
                firebaseService.setStep(!step)
                button.setBackgroundResource(if (step) R.drawable.x else R.drawable.o)
                firebaseService.setBoardStateSimple(listBD)
            }
        }
    }
    private fun disableAllButtons() {
        buttonArr.forEach { it.isClickable = false }
    }

    private fun getBoardState(callback: (MutableList<Int>, Boolean) -> Unit) {
        val databaseReference = database.getReference("Stat/Place")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<Int>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { 0 }
                val gameStatus = firebaseService.getStep(boardState)
                gameBoard.updateBoardAllSimple(boardState, buttonArr)

                callback(boardState, gameStatus)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(MutableList(9) { 0 }, false)

                Log.e("ooo", "Error: ${error.message}")
            }
        })
    }

    private fun setupFirebaseListener() {
        val databaseReference = database.getReference("Stat/Place")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<Int>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { 0 }
                updateUI(boardState)
                when {
                    gameBoard.checkWin(1, boardState) -> handleWin("X", 1)
                    gameBoard.checkWin(2, boardState) -> handleWin("O", 2)
                    gameBoard.isDraw(boardState) -> handleDraw()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooo", "Error: ${error.message}")
            }
        })
    }

    private fun updateUI(boardState: MutableList<Int>) {
        for ((index, button) in buttonArr.withIndex()) {
            when (boardState[index]) {
                1 -> button.setBackgroundResource(R.drawable.x)
                2 -> button.setBackgroundResource(R.drawable.o)
                else -> button.setBackgroundResource(R.drawable.white_background)
            }
        }
    }

    private fun handleWin(player: String, winCode: Int) {
        Toast.makeText(this, "Win $player", Toast.LENGTH_LONG).show()
        firebaseService.setWin(winCode)
        replaceFragment(win_fragment())
    }

    private fun handleDraw() {
        Toast.makeText(this, "DRAW", Toast.LENGTH_LONG).show()
        firebaseService.setWin(0)
        replaceFragment(win_fragment())
    }

    private fun resetGame() {
        gameBoard.resetBoardSimple()
        firebaseService.setBoardStateSimple(gameBoard.getBoardState())
        buttonArr.forEach { it.setBackgroundResource(R.drawable.white_background) }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentConteiner.id, fragment)
        fragmentTransaction.commit()
        binding.buttonReset.visibility = View.GONE
        binding.TicTacToeText.visibility = View.GONE
        binding.bSuper.visibility = View.GONE
        disableAllButtons()
    }
}
