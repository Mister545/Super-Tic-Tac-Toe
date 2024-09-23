package com.exemple.ticktacktoe.Game.Online

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.exemple.ticktacktoe.DialogHelper.DialogHelper
import com.exemple.ticktacktoe.Firebase.FirebasePatches
import com.exemple.ticktacktoe.databinding.ActivitySimpleTicTacToeBinding
import com.exemple.ticktacktoe.Firebase.FirebaseService
import com.exemple.ticktacktoe.Game.ConstForActivity
import com.exemple.ticktacktoe.Game.MainActivity
import com.exemple.ticktacktoe.GameBoard
import com.exemple.ticktacktoe.R
import com.exemple.ticktacktoe.Servers.FragmentWaitingPlayers
import com.exemple.ticktacktoe.Servers.FragmentWaitingPlayersRoom
import com.exemple.ticktacktoe.Servers.Servers
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class SimpleTicTacToe : AppCompatActivity() {

    private lateinit var binding: ActivitySimpleTicTacToeBinding
    private lateinit var buttonArr: List<Button>
    private val gameBoard = GameBoard()
    private val firebaseService = FirebaseService()
    private val database = FirebaseDatabase.getInstance()
    private var firebaseListener: ValueEventListener? = null
    private val servers = Servers()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleTicTacToeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FragmentWaitingPlayers().apply {
            arguments = Bundle().apply {
                putString(ConstForActivity.SIMPLE_OR_SUPER.toString(), "simple")
            }
        }

        buttonArr = arrayListOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9,
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

            firebaseService.getPlayersNumSimple(FirebasePatches.playersNum) {
                firebaseService.setPlayersNum(it - 1, FirebasePatches.playersNum)
            }
            removeFragment()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        firebaseService.setBoardStateSimple(MutableList(9){0}, FirebasePatches.boardStateSimple)
        firebaseService.setStep(true, FirebasePatches.stepSimple)
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
        database.getReference(FirebasePatches.playersNum).removeEventListener(firebaseService.getPlayersNumSimpleEvent(
            FirebasePatches.playersNum) {})

        val exitCodeListener: ValueEventListener? = null

        exitCodeListener?.let { listener ->
            firebaseService.removeListener(FirebasePatches.statSimpleData, listener)
        }
        firebaseListener = null
    }
    private fun clearDataFromFirebase() {
        val databaseReference = database.getReference(FirebasePatches.boardStateSuper)
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
            val isWaitingPlayer = servers.waitingPlayer(FirebasePatches.playersNum, 0, 0){
                if (!it) {
                    val value = intent.getStringExtra(ConstForActivity.ROOM_CODE.toString())
                    if (value == "true"){
                        replaceFragment(FragmentWaitingPlayersRoom())
                    } else {
                        replaceFragment(FragmentWaitingPlayers())
                    }
//                    disableAllButtons()
                } else {
                    removeFragment()
                    enableAllButtons()
                }
            }
            Log.d("ooo", isWaitingPlayer.toString())
        }
    }


    private fun disableAllButtons() {
        buttonArr.forEach { it.isClickable = false
        binding.bComeBackSimple.isClickable = false}
    }
    private fun enableAllButtons() {
        buttonArr.forEach { it.isClickable = true
        binding.bComeBackSimple.isClickable = true}
    }



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
                    gameBoard.checkWin(1, boardState) -> DialogHelper().createWinDialogSimple("X", this@SimpleTicTacToe)
                    gameBoard.checkWin(2, boardState) -> DialogHelper().createWinDialogSimple("O", this@SimpleTicTacToe)
                    gameBoard.isDraw(boardState) -> DialogHelper().createWinDialogSimple("Draw", this@SimpleTicTacToe)
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
}