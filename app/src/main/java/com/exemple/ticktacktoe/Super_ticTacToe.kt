package com.exemple.ticktacktoe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.exemple.ticktacktoe.databinding.ActivityMainBinding
import com.exemple.ticktacktoe.databinding.FragmentSuperTicTacToeBinding
import com.exemple.ticktacktoe.ui.theme.Game.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import java.io.File


class SuperTicTacToe : AppCompatActivity() {

    private lateinit var binding: FragmentSuperTicTacToeBinding
    private lateinit var buttonArrWithArr: List<List<Button>>
    private lateinit var buttonArrAll: List<Button>
    private val database = FirebaseDatabase.getInstance()
    private val firebaseService = FirebaseService()
    private val gameBoard = GameBoard()
    private lateinit var firebaseListener: ValueEventListener
    private var boardStateListener: ValueEventListener? = null
    private var nextBoardListener: ValueEventListener? = null
    private var nextFieldListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSuperTicTacToeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.resetSuper.setOnClickListener {
            // resetUi()
        }
        binding.bComeBackSuper.setOnClickListener {
            finish()

            Log.d("ooo", "${gameBoard.getBoardSuper()}")
        }
    }

//    override fun onPause() {
//        super.onPause()
//        resetGame()
//    }

    override fun onResume() {
        super.onResume()
        firebaseService.setNextBoard(10)
        firebaseService.setBoardStateSuper(gameBoard.getBoardSuper())
        setupButtons()
        initialization()
        setupFirebaseListenerAndChecker() // Перемістіть сюди
    }

    override fun onDestroy() {
        super.onDestroy()
        // Очищення кешованих файлів
        val cacheDir = cacheDir
        deleteDir(cacheDir)
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir == null || !dir.isDirectory) return false
        val children = dir.list()
        if (children != null) {
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) return false
            }
        }
        return dir.delete()
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
        setupButtonListeners()
        setupFirebaseListenerAndChecker()
        resetGame()
    }

    private fun setStoke() {

        getBoardState { board, _ ->
            for (i in board.indices) {
                for (j in board[i].indices) {
                    gameBoard.setStrokeOnButtonBlack(
                        buttonArrWithArr[i][j],
                        this
                    )
                }
            }
        }

        getNextBoard { nextBoard ->

            for (j in nextField(nextBoard)) {
                if (j !in buttonArrWithArr.indices) {
                    Log.e("Error", "Invalid index: $j")
                    continue
                }

                buttonArrWithArr[j].forEachIndexed { index, button ->
                    gameBoard.setStrokeOnButtonGreen(
                        button,
                        this
                    )

                }
            }
        }
    }


    private fun setupButtonListeners() {
        getNextBoard { nextBoard ->
            disableAllButtons()
            if (gameBoard.checkRightPlace(nextBoard)) {
                disableBoardWinner(nextBoard)
                firebaseService.setNextBoard(10)
                buttonArrWithArr.forEachIndexed { i, _ -> enableBoardButtons(i) }
            } else if (nextBoard == 10) {
                buttonArrWithArr.forEachIndexed { i, _ -> enableBoardButtons(i) }
            } else {
                enableBoardButtons(nextBoard)
            }
            firebaseService.setNextField(nextField(nextBoard))
            setStoke()
        }
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
            Log.d("ooo", "Updated arr: $arr") // Логування оновленого масиву
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

        buttonArrWithArr.flatten().forEach { it.isClickable = false }
    }

    private fun disableButtonsIsNotNull(array: MutableList<MutableList<Int>>) {

        buttonArrWithArr.forEachIndexed { indexButton, button ->
            array.forEachIndexed { index, ints ->
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
                firebaseService.setNextBoard(10)
            } else {
                button.setOnClickListener {
                    updateBoard(boardIndex, index, button)
                    firebaseService.setNextBoard(index)
                }
                getBoardState { mutableLists, _ ->
                    disableButtonsIsNotNull(mutableLists)
                }
            }
        }
    }

    private fun updateBoard(boardIndex: Int, index: Int, button: Button) {
        getBoardState { listBD, step ->
            if (listBD[boardIndex][index] == 0) {
                val currentPlayer = if (!step) 2 else 1
                listBD[boardIndex][index] = currentPlayer
                firebaseService.setStepSuper(!step)
                button.text = (if (step) "X" else "O")
                firebaseService.setBoardStateSuper(listBD)
            }
        }
    }
    private fun getNextBoard(callback: (Int) -> Unit) {
        val databaseReference = database.getReference("Stat/prevStep")
        nextBoardListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val nextBoard = dataSnapshot.getValue(Int::class.java)!!
                callback(nextBoard)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Помилка зчитування з Firebase: ${error.message}")
            }
        }
        databaseReference.addValueEventListener(nextBoardListener!!)
    }

    private fun getBoardState(callback: (MutableList<MutableList<Int>>, Boolean) -> Unit) {
        val databaseReference = database.getReference("Stat/data")
        boardStateListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<MutableList<Int>>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { MutableList(9) { 0 } }
                val gameStatus = firebaseService.getStepSuper(boardState)
                callback(boardState, gameStatus)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooo", "Error: ${error.message}")
                callback(MutableList(9) { MutableList(9) { 0 } }, false)
            }
        }
        databaseReference.addValueEventListener(boardStateListener!!)
    }

    private fun getNextField(callback: (MutableList<Int>) -> Unit) {
        val databaseReference = database.getReference("Stat/nextField")
        nextFieldListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<Int>>() {}
                val nextField = dataSnapshot.getValue(t) ?: MutableList(9) { 0 }
                callback(nextField)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(MutableList(9) { 0 })
                Log.e("ooo", "Error: ${error.message}")
            }
        }
        databaseReference.addValueEventListener(nextFieldListener!!)
    }
    private fun setupFirebaseListenerAndChecker() {
        val databaseReference = database.getReference("Stat/data")
        firebaseListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<MutableList<Int>>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { MutableList(9) { 0 } }
                updateUI(boardState)
                gameBoard.checkWinSuper(boardState, binding)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooo", "Error: ${error.message}")
            }
        }
        databaseReference.addValueEventListener(firebaseListener)
    }

    private fun updateUI(boardState: MutableList<MutableList<Int>>) {

        boardState.flatten().forEachIndexed { index, value ->
            val button = buttonArrAll[index]
            when (value) {
                1 -> button.text = "X"
                2 -> button.text = "O"
                else -> gameBoard.setBackgroundButtonsSuper(this, button)
            }
        }
    }


    private fun resetGame() {
//        gameBoard.resetBoardSuper()

        firebaseService.setBoardStateSuper(MutableList(9) { MutableList(9) { 0 } })
        buttonArrAll.forEach { gameBoard.setBackgroundButtonsSuper(this, it) }
    }

    private fun handleWin(player: String, winCode: Int) {
        binding.TextWin.text = "Win $player"
        binding.TextWin.setTextColor(ContextCompat.getColor(this, R.color.green))
        binding.TextWin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50f)
        firebaseService.setWin(winCode)
    }

    private fun handleDraw() {
        binding.TextWin.text = "Draw"
        binding.TextWin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50f)
        firebaseService.setWinSuper(0)
    }
}

