package com.exemple.ticktacktoe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.exemple.ticktacktoe.databinding.ActivitySimpleTicTacToeBinding
import com.exemple.ticktacktoe.Game.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class SimpleTicTacToe : AppCompatActivity() {



    private lateinit var binding: ActivitySimpleTicTacToeBinding
    private lateinit var buttonArr: List<Button>
    private val gameBoard = GameBoard()
    val firebaseService = FirebaseService()
    private val database = FirebaseDatabase.getInstance()
    private var firebaseListener: ValueEventListener? = null
    private val servers = Servers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleTicTacToeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonArr = arrayListOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )

        firebaseService.getPlayersNumSimple(FirebasePatches.playersNum){
            if (Servers.ServersSimple.serverIsStarting(FirebasePatches.playersNum, it)){
                initialization()
                serverWaitingPlayer()
            }
        }


        binding.bComeBackSimple.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            firebaseService.setExitCode(1, FirebasePatches.exitCodeSimple)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListeners()
    }
    private fun initialization() {
        firebaseService.setExitCode(0, FirebasePatches.exitCodeSimple)
        exitListeners()
        setupButtonListeners()
        firebaseService.setStep(true, FirebasePatches.stepSimple)
        resetGame()
        setupFirebaseListener()  // Налаштування постійного слухача змін
    }
    private fun exitListeners() {
        firebaseService.getExitCode(FirebasePatches.exitCodeSimple) { code ->
            if (code == 1)
                exitWithActivity()
        }
    }
    private fun exitWithActivity() {
        removeListeners()
        finish()

    }
    private fun removeListeners() {
        val databaseReference = database.getReference(FirebasePatches.refSimple)
        firebaseListener?.let {
            databaseReference.removeEventListener(it)
        }

        firebaseListener = null
    }
    private fun clearDataFromFirebase() {
        val databaseReference = database.getReference(FirebasePatches.refSimple)
        databaseReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Data removed successfully.")
            } else {
                Log.e("Firebase", "Failed to remove data.", task.exception)
            }
        }
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
                firebaseService.setStep(!step, FirebasePatches.stepSimple)
                button.text = (if (step) "X" else "O")
                firebaseService.setBoardStateSimple(listBD, FirebasePatches.boardStateSimple)
            }
        }
    }
    private fun serverWaitingPlayer(){
        firebaseService.getPlayersNumSimpleEvent(FirebasePatches.playersNum) {
            val isWaitingPlayer = servers.waitingPlayer(FirebasePatches.playersNum, 0){
                if (!it) {
                    binding.TicTacToeText.text = "wait"
                } else {
                    binding.TicTacToeText.text = "Start"
                }
            }
            Log.d("ooo", isWaitingPlayer.toString())
        }

    }


    private fun disableAllButtons() {
        buttonArr.forEach { it.isClickable = false }
    }
    private fun enableAllButtons() {
        buttonArr.forEach { it.isClickable = true }
    }

//    private fun getBoardState(callback: (MutableList<Int>, Boolean) -> Unit) {
//        val databaseReference = database.getReference(FirebasePatches.getBoardStateSimple())
//        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val t = object : GenericTypeIndicator<MutableList<Int>>() {}
//                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { 0 }
//                val gameStatus = firebaseService.getStep(boardState)
//                gameBoard.updateBoardAllSimple(this@SimpleTicTacToe, boardState, buttonArr)
//
//                callback(boardState, gameStatus)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                callback(MutableList(9) { 0 }, false)
//
//                Log.e("ooo", "Error: ${error.message}")
//            }
//        })
//    }

    private fun getBoardState(callback: (MutableList<Int>, Boolean) -> Unit) {
        val databaseReference = database.getReference(FirebasePatches.refSimple)

        firebaseListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val boardState = dataSnapshot.child(FirebasePatches.data)
                    .getValue(object : GenericTypeIndicator<MutableList<Int>>() {})
                val gameStatus = firebaseService.getStep(boardState!!)

                callback(boardState, gameStatus)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooo", "Error: ${error.message}")
            }
        }
        databaseReference.addValueEventListener(firebaseListener!!)
    }

    private fun setupFirebaseListener() {
        val databaseReference = database.getReference(FirebasePatches.setupFirebaseListener)
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
        databaseReference.addValueEventListener(firebaseListener!!)
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
        firebaseService.setBoardStateSimple(gameBoard.getBoardState(), FirebasePatches.boardStateSimple)
        buttonArr.forEach { gameBoard.setBackgroundButtonsSimple(this, it) }
    }

    private fun resetUi(){
        buttonArr.forEachIndexed { _, button ->
            button.text = ""
        }
        initialization()
    }
}
