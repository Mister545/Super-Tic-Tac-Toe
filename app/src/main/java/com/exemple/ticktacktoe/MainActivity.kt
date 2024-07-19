// MainActivity.kt
package com.exemple.ticktacktoe

import android.os.Bundle
import android.util.Log
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
    private val firebaseService = FirebaseService()
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonArr = arrayListOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )

        initialization()

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
                val currentPlayer = if (step) 1 else 2
                listBD[index] = currentPlayer
                firebaseService.setStep(!step)

                button.setBackgroundResource(if (step) R.drawable.x else R.drawable.o)

                firebaseService.setBoardState(listBD)
//                checkGameStatus()
                gameBoard.switchPlayer()
            }
        }
    }

//    private fun checkGameStatus() {
//        when {
//            gameBoard.checkWin(1) -> handleWin("X", 1)
//            gameBoard.checkWin(2) -> handleWin("O", 2)
//            gameBoard.isDraw() -> handleDraw()
//        }
//    }

    fun getBoardState(callback: (MutableList<Int>, Boolean) -> Unit) {
        val databaseReference = database.getReference("Stat/Place")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<Int>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { 0 }
                val gameStatus = firebaseService.step(boardState)
                gameBoard.updateBoardAll(boardState, buttonArr, binding)

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
//                checkGameStatus()
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
//        checkGameStatus()
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
        firebaseService.getBoardState { board, step ->
            gameBoard.switchPlayer()
            if (!step) gameBoard.switchPlayer()
        }

        gameBoard.resetBoard()
        firebaseService.setBoardState(gameBoard.getBoardState())
        buttonArr.forEach { it.setBackgroundResource(R.drawable.white_background) }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentConteiner.id, fragment)
        fragmentTransaction.commit()
    }
}
