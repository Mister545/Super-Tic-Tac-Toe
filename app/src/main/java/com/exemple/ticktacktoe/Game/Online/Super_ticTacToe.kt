package com.exemple.ticktacktoe.Game.Online

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.exemple.ticktacktoe.Firebase.FirebasePatches
import com.exemple.ticktacktoe.databinding.ActivitySuperTicTacToeBinding
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


class SuperTicTacToe : AppCompatActivity() {

    private lateinit var binding: ActivitySuperTicTacToeBinding
    private lateinit var buttonArrWithArr: List<List<Button>>
    private lateinit var buttonArrAll: List<Button>
    private val database = FirebaseDatabase.getInstance()
    private val firebaseService = FirebaseService()
    private val gameBoard = GameBoard()
    private val servers = Servers()
    private var firebaseListener: ValueEventListener? = null
    private var boardStateListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperTicTacToeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FragmentWaitingPlayers().apply {
            arguments = Bundle().apply {
                putString(ConstForActivity.SIMPLE_OR_SUPER.toString(), "super")
            }
        }

        setupButtons()
        setStyleOnAllButtons()

            firebaseService.getPlayersNumSuper(FirebasePatches.playersNumSuper){
            if (Servers.ServersSuper.serverIsStarting(FirebasePatches.playersNumSuper, it)){
                initialization()
                serverWaitingPlayer()
            }
        }

        binding.bComeBackSuper.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            firebaseService.setExitCode(1, FirebasePatches.exitCodeSuper)

            firebaseService.getPlayersNumSuper(FirebasePatches.playersNumSuper) {
                firebaseService.setPlayersNumSuper(it - 1, FirebasePatches.playersNumSuper)
            }
            removeFragment()
        }
    }

    private fun setStyleOnAllButtons() {
        buttonArrWithArr.forEach {
            it.forEach {
                gameBoard.setBackgroundButtonsSuper(
                    this, it
                ) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListeners()
    }

    private fun setupButtons() {
        buttonArrWithArr = listOf(
            listOf(
                binding.button000000,
                binding.button000101,
                binding.button000202,
                binding.button000303,
                binding.button000404,
                binding.button000505,
                binding.button000606,
                binding.button000707,
                binding.button000808
            ),
            listOf(
                binding.button010009,
                binding.button010110,
                binding.button010211,
                binding.button010312,
                binding.button010413,
                binding.button010514,
                binding.button010615,
                binding.button010716,
                binding.button010817
            ),
            listOf(
                binding.button020018,
                binding.button020119,
                binding.button020220,
                binding.button020321,
                binding.button020422,
                binding.button020523,
                binding.button020624,
                binding.button020725,
                binding.button020826
            ),
            listOf(
                binding.button030027,
                binding.button030128,
                binding.button030229,
                binding.button030330,
                binding.button030431,
                binding.button030532,
                binding.button030633,
                binding.button030734,
                binding.button030835
            ),
            listOf(
                binding.button040036,
                binding.button040137,
                binding.button040238,
                binding.button040339,
                binding.button040440,
                binding.button040541,
                binding.button040642,
                binding.button040743,
                binding.button040844
            ),
            listOf(
                binding.button050045,
                binding.button050146,
                binding.button050247,
                binding.button050348,
                binding.button050449,
                binding.button050550,
                binding.button050651,
                binding.button050752,
                binding.button050853
            ),
            listOf(
                binding.button060054,
                binding.button060155,
                binding.button060256,
                binding.button060357,
                binding.button060458,
                binding.button060559,
                binding.button060660,
                binding.button060761,
                binding.button060862
            ),
            listOf(
                binding.button070063,
                binding.button070164,
                binding.button070265,
                binding.button070366,
                binding.button070467,
                binding.button070568,
                binding.button070669,
                binding.button070770,
                binding.button070871
            ),
            listOf(
                binding.button080072,
                binding.button080173,
                binding.button080274,
                binding.button080375,
                binding.button080476,
                binding.button080577,
                binding.button080678,
                binding.button080779,
                binding.button080880
            )
        )
        buttonArrAll = buttonArrWithArr.flatten()
    }

    private fun initialization() {
        firebaseService.setExitCode(0, FirebasePatches.exitCodeSuper)
        exitListeners()
        firebaseService.setNextField(MutableList(9) { 0 }, FirebasePatches.nextField)
        firebaseService.setWinSuper(MutableList(9) { 0 }, FirebasePatches.winSuper)
        firebaseService.setBoardStateSuper(
            MutableList(9) { MutableList(9) { 0 } },
            FirebasePatches.boardStateSuper
        )
        setupFirebaseListenerAndChecker()
        firebaseService.setNextBoard(10, FirebasePatches.nextBoard)
        firebaseService.setStepSuper(true, FirebasePatches.stepSuper)
        resetGame()
    }

    private fun exitWithActivity() {
        removeListeners()
        finish()
    }

    private fun clearDataFromFirebase() {
        val databaseReference = database.getReference(FirebasePatches.refSuper)
        databaseReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "Data removed successfully.")
            } else {
                Log.e("Firebase", "Failed to remove data.", task.exception)
            }
        }
    }
    private fun serverWaitingPlayer(){
        firebaseService.getPlayersNumSuperEvent(FirebasePatches.playersNumSuper) {

            val isWaitingPlayer = servers.waitingPlayer(FirebasePatches.playersNumSuper, 2, 1){
                if (!it) {
                    val value = intent.getStringExtra(ConstForActivity.ROOM_CODE.toString())
                    if (value == "true"){
                        replaceFragment(FragmentWaitingPlayersRoom())
                    } else {
                        replaceFragment(FragmentWaitingPlayers())
                    }
//                    disableAllButtons()
                } else {
                    enableAllButtons()
                    removeFragment()
                }
            }
            Log.d("ooo", isWaitingPlayer.toString())
        }
    }

    private fun exitListeners() {
        firebaseService.getExitCode(FirebasePatches.exitCodeSuper) { code ->
            if (code == 1)
                exitWithActivity()
        }
    }

    private fun removeListeners() {
        val databaseReference = database.getReference(FirebasePatches.refSuper)
        boardStateListener?.let {
            databaseReference.removeEventListener(it)
        }
        firebaseListener?.let {
            databaseReference.removeEventListener(it)
        }
        boardStateListener = null
        firebaseListener = null
    }


    private fun setStoke(nextBoard: Int) {

        getBoardState { board, _ ->
            for (i in board.indices) {
                for (j in board[i].indices) {
                    gameBoard.setStrokeOnButtonOff(
                        buttonArrWithArr[i][j],
                        this
                    )
                }
            }

            for (j in nextField(nextBoard)) {
                if (j !in buttonArrWithArr.indices) {
                    Log.e("Error", "Invalid index: $j")
                    continue
                }

                buttonArrWithArr[j].forEachIndexed { _, button ->
                    gameBoard.setStrokeOnButtonOn(
                        button,
                        this
                    )
                }
            }
        }
    }

    private fun handleBoardUpdates(nextBoard: Int) {
        setupButtonListeners(nextBoard)
    }

    private fun setupButtonListeners(nextBoard: Int) {
        disableAllButtons()
        if (gameBoard.checkRightPlace(nextBoard)) {
            disableBoardWinner(nextBoard)
            firebaseService.setNextBoard(10, FirebasePatches.nextBoard)
            firebaseService.setNextField(nextField(10), FirebasePatches.nextField)
            buttonArrWithArr.forEachIndexed { i, _ -> enableBoardButtons(i) }
        } else if (nextBoard == 10) {
            buttonArrWithArr.forEachIndexed { i, _ -> enableBoardButtons(i) }
        } else {
            enableBoardButtons(nextBoard)
        }
        setStoke(nextBoard)
    }





    private fun nextField(nextBoard: Int): MutableList<Int> {
        val arr = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
        val empty = -1

        val winListSuper = gameBoard.getWinListSuper()
        return if (nextBoard == 10 || prevStepInWinListSuper(nextBoard, winListSuper)) {
            winListSuper.forEachIndexed { index, i ->
                if (i in arr.indices && i != 0) { // Перевірка, чи індекс знаходиться в межах допустимого діапазону
                    arr[index] = empty
                }
            }
            Log.d("ooo", "Updated arr: $arr")
            arr
        } else {
            mutableListOf(nextBoard)
        }
    }

    private fun prevStepInWinListSuper(nextBoard: Int, winListSuper: MutableList<Int>): Boolean {
        winListSuper.forEachIndexed { index, i ->
            return i != 0 && index == nextBoard
        }
        return false
    }

    private fun disableAllButtons() {

        buttonArrWithArr.flatten().forEach { it.isClickable = false
            binding.bComeBackSuper.isClickable = false}
    }
    private fun enableAllButtons() {

        buttonArrWithArr.flatten().forEach { it.isClickable = true
        binding.bComeBackSuper.isClickable = true}
    }

    private fun disableButtonsIsNotNull(array: MutableList<MutableList<Int>>) {

        buttonArrWithArr.forEachIndexed { indexButton, button ->
            array.forEachIndexed { index, _ ->
                if (array[indexButton][index] != 0)
                    button[index].isClickable = false
            }
        }
    }

    private fun disableBoardWinner(i: Int) {
        buttonArrWithArr[i].forEach { it.isClickable = false }
    }

    private fun enableBoardButtons(boardIndex: Int) {
        buttonArrWithArr[boardIndex].forEachIndexed { index, button ->
            button.isClickable = true
            if (gameBoard.checkRightPlace(boardIndex)) {
                Log.d("ooo", "chacking======${gameBoard.checkRightPlace(boardIndex)}")
                disableBoardWinner(boardIndex)
                firebaseService.setNextBoard(10, FirebasePatches.nextBoard)
                firebaseService.setNextField(nextField(10), FirebasePatches.nextField)
            } else {
                button.setOnClickListener {
                    updateBoard(boardIndex, index, button)
                    firebaseService.setNextBoard(index, FirebasePatches.nextBoard)
                    firebaseService.setNextField(nextField(index), FirebasePatches.nextField)
                }
            }
        }
    }
    private fun updateBoard(boardIndex: Int, index: Int, button: Button) {
        getBoardState { listBD, step ->
            if (listBD[boardIndex][index] == 0) {
                val currentPlayer = if (!step) 2 else 1
                listBD[boardIndex][index] = currentPlayer
                firebaseService.setStepSuper(!step, FirebasePatches.stepSuper)
                button.text = if (step) "X" else "O"
                firebaseService.setBoardStateSuper(listBD, FirebasePatches.boardStateSuper)
            }
        }
    }

    private fun getBoardState(callback: (MutableList<MutableList<Int>>, Boolean) -> Unit) {
        val databaseReference = database.getReference(FirebasePatches.boardStateSuper)

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<MutableList<Int>>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { MutableList(9) { 0 } }
                val gameStatus = firebaseService.getStepSuper(boardState)
                binding.textIsNextX.text =
                    if (firebaseService.getStepSuper(boardState)) "next step X" else "next step O"

                callback(boardState, gameStatus)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooo", "Error: ${error.message}")
                callback(MutableList(9) { MutableList(9) { 0 } }, false)
            }
        })
    }

    private fun setupFirebaseListenerAndChecker() {
        val databaseReference = database.getReference(FirebasePatches.refSuper)

        firebaseListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val boardState = dataSnapshot.child(FirebasePatches.data).getValue(object : GenericTypeIndicator<MutableList<MutableList<Int>>>() {})
                    ?: MutableList(9) { MutableList(9) { 0 } }
                val nextBoard =
                    dataSnapshot.child(FirebasePatches.prevStep).getValue(Int::class.java) ?: 10
                updateUI(boardState)
                gameBoard.checkWinSuper(boardState, binding, this@SuperTicTacToe)
                firebaseService.setWinSuper(gameBoard.getWinListSuper(), FirebasePatches.winSuper)
                handleBoardUpdates(nextBoard)
                firebaseService.setNextField(nextField(nextBoard), FirebasePatches.nextField)
                disableButtonsIsNotNull(boardState)
                Log.d("ooo", "boardState=========$boardState")
                Log.d("ooo", "nextBoard=========$nextBoard")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooo", "Error: ${error.message}")
            }
        }
        databaseReference.addValueEventListener(firebaseListener!!)
    }

    private fun updateUI(boardState: MutableList<MutableList<Int>>) {

        boardState.flatten().forEachIndexed { index, value ->
            val button = buttonArrAll[index]
            when (value) {
                1 -> button.text = "X"
                2 -> button.text = "O"
                else -> button.text = " "
            }
        }
    }

    private fun resetGame() {
        firebaseService.setBoardStateSuper(
            MutableList(9) { MutableList(9) { 0 } },
            FirebasePatches.boardStateSuper
        )
        buttonArrAll.forEach { gameBoard.setBackgroundButtonsSuper(this, it) }
    }

    //    private fun handleWin(player: String, winCode: Int) {
//        binding.TextWin.text =  getString(R.string.winText, player)
//        binding.TextWin.setTextColor(ContextCompat.getColor(this, R.color.green))
//        binding.TextWin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50f)
//        firebaseService.setWin(winCode)
//    }
//
//    private fun handleDraw() {
//        binding.TextWin.text = getString(R.string.drawText)
//        binding.TextWin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50f)
//        firebaseService.setWinSuper(0)
//    }
    private fun replaceFragment(fragment: DialogFragment) {
        // Перевірка чи активність не знищена і не завершується
        if (!isFinishing && !supportFragmentManager.isDestroyed) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            // Перевірка чи транзакція безпечна (стан активності не був збережений)
            if (!supportFragmentManager.isStateSaved) {
                fragmentTransaction.replace(binding.fragmentContainerView.id, fragment)
                fragmentTransaction.commit()
            } else {
                // Якщо стан активності був збережений, використовуйте commitAllowingStateLoss
                fragmentTransaction.replace(binding.fragmentContainerView.id, fragment)
                fragmentTransaction.commitAllowingStateLoss()
            }
        } else {
            // Логування або інші дії, якщо транзакція не може бути виконана
            println("Замінити фрагмент неможливо: активність знищена або завершується")
        }
    }

    private fun removeFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        if (fragment != null && !supportFragmentManager.isStateSaved) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }
    }

}

