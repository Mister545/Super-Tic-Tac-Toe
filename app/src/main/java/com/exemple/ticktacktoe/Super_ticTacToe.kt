package com.exemple.ticktacktoe

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.exemple.ticktacktoe.databinding.FragmentSuperTicTacToeBinding
import com.exemple.ticktacktoe.ui.theme.Game.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson


class SuperTicTacToe : DialogFragment() {

    private lateinit var binding: FragmentSuperTicTacToeBinding
    private lateinit var buttonArr0: List<Button>
    private lateinit var buttonArr1: List<Button>
    private lateinit var buttonArr2: List<Button>
    private lateinit var buttonArr3: List<Button>
    private lateinit var buttonArr4: List<Button>
    private lateinit var buttonArr5: List<Button>
    private lateinit var buttonArr6: List<Button>
    private lateinit var buttonArr7: List<Button>
    private lateinit var buttonArr8: List<Button>
    private lateinit var buttonArrAll: List<Button>
    val firebaseService = FirebaseService()
    val database = FirebaseDatabase.getInstance()
    val gameBoard = GameBoard()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuperTicTacToeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonArr0 = arrayListOf(
            binding.button000000, binding.button000101, binding.button000202,
            binding.button000303, binding.button000404, binding.button000505,
            binding.button000606, binding.button000707, binding.button000808
        )
        buttonArr1 = arrayListOf(
            binding.button010009, binding.button010110, binding.button010211,
            binding.button010312, binding.button010413, binding.button010514,
            binding.button010615, binding.button010716, binding.button010817
        )
        buttonArr2 = arrayListOf(
            binding.button020018, binding.button020119, binding.button020220,
            binding.button020321, binding.button020422, binding.button020523,
            binding.button020624, binding.button020725, binding.button020826
        )
        buttonArr3 = arrayListOf(
            binding.button030027, binding.button030128, binding.button030229,
            binding.button030330, binding.button030431, binding.button030532,
            binding.button030633, binding.button030734, binding.button030835
        )
        buttonArr4 = arrayListOf(
            binding.button040036, binding.button040137, binding.button040238,
            binding.button040339, binding.button040440, binding.button040541,
            binding.button040642, binding.button040743, binding.button040844
        )
        buttonArr5 = arrayListOf(
            binding.button050045, binding.button050146, binding.button050247,
            binding.button050348, binding.button050449, binding.button050550,
            binding.button050651, binding.button050752, binding.button050853
        )
        buttonArr6 = arrayListOf(
            binding.button060054, binding.button060155, binding.button060256,
            binding.button060357, binding.button060458, binding.button060559,
            binding.button060660, binding.button060761, binding.button060862
        )
        buttonArr7 = arrayListOf(
            binding.button070063, binding.button070164, binding.button070265,
            binding.button070366, binding.button070467, binding.button070568,
            binding.button070669, binding.button070164, binding.button070871
        )
        buttonArr8 = arrayListOf(
            binding.button080072, binding.button080173, binding.button080274,
            binding.button080375, binding.button080476, binding.button080577,
            binding.button080678, binding.button080779, binding.button080880
        )
        buttonArrAll = arrayListOf(
            binding.button000000, binding.button000101, binding.button000202,
            binding.button000303, binding.button000404, binding.button000505,
            binding.button000606, binding.button000707, binding.button000808,

            binding.button010009, binding.button010110, binding.button010211,
            binding.button010312, binding.button010413, binding.button010514,
            binding.button010615, binding.button010716, binding.button010817,

            binding.button020018, binding.button020119, binding.button020220,
            binding.button020321, binding.button020422, binding.button020523,
            binding.button020624, binding.button020725, binding.button020826,

            binding.button030027, binding.button030128, binding.button030229,
            binding.button030330, binding.button030431, binding.button030532,
            binding.button030633, binding.button030734, binding.button030835,

            binding.button040036, binding.button040137, binding.button040238,
            binding.button040339, binding.button040440, binding.button040541,
            binding.button040642, binding.button040743, binding.button040844,

            binding.button050045, binding.button050146, binding.button050247,
            binding.button050348, binding.button050449, binding.button050550,
            binding.button050651, binding.button050752, binding.button050853,

            binding.button060054, binding.button060155, binding.button060256,
            binding.button060357, binding.button060458, binding.button060559,
            binding.button060660, binding.button060761, binding.button060862,

            binding.button070063, binding.button070164, binding.button070265,
            binding.button070366, binding.button070467, binding.button070568,
            binding.button070669, binding.button070770, binding.button070871,

            binding.button080072, binding.button080173, binding.button080274,
            binding.button080375, binding.button080476, binding.button080577,
            binding.button080678, binding.button080779, binding.button080880
        )
        val board0 = MutableList(9) { 0 }
        val board1 = MutableList(9) { 0 }
        val board2 = MutableList(9) { 0 }
        val board3 = MutableList(9) { 0 }
        val board4 = MutableList(9) { 0 }
        val board5 = MutableList(9) { 0 }
        val board6 = MutableList(9) { 0 }
        val board7 = MutableList(9) { 0 }
        val board8 = MutableList(9) { 0 }

        val boardAll = mutableListOf(
            board0, board1, board2,
            board3, board4, board5,
            board6, board7, board8
        )

        firebaseService.setBoardStateSuper(boardAll)
        initialization()

    }

    private fun initialization() {
        setupButtonListeners()
        resetGame()
        setupFirebaseListener()  // Налаштування постійного слухача змін
    }

    private fun setupButtonListeners() {
        for ((index, button) in buttonArrAll.withIndex()) {
            button.setOnClickListener {
                updateBoard(index, button)
            }
        }
    }

    private fun updateBoard(index: Int, button: Button) {
        getBoardState { listBD, step ->
            val row = index / 9
            val col = index % 9

            if (listBD[row][col] == 0) {
                val currentPlayer = if (!step) 2 else 1
                listBD[row][col] = currentPlayer
                firebaseService.setStepSuper(!step)
                button.setBackgroundResource(if (step) R.drawable.x else R.drawable.o)
                firebaseService.setBoardStateSuper(listBD)
            }
        }
    }


    private fun getBoardState(callback: (MutableList<MutableList<Int>>, Boolean) -> Unit) {
        val databaseReference = database.getReference("Stat/Board/Super")
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<MutableList<Int>>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { MutableList(9)  {0} }
                val gameStatus = firebaseService.getStepSuper(boardState)
                gameBoard.updateBoardAllSuper(boardState, buttonArrAll)

                callback(boardState, gameStatus)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(MutableList(9) { MutableList(9) { 0 } }, false)

                Log.e("ooo", "Error: ${error.message}")
            }
        })
    }

    private fun setupFirebaseListener() {
        val databaseReference = database.getReference("Stat/Board/Super")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<MutableList<Int>>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { MutableList(9)  {0}}
                    updateUI(boardState)
                    val gson = Gson()
                    val boardStateJson = gson.toJson(boardState)
                    Log.d("SuperTicTacToe", boardStateJson)
                    saveInArraysAndCheck(boardState)
                    firebaseService.getStepSuper(boardState)
                    val o = firebaseService.getStepSuper(boardState)
                    Log.d("ppp", o.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooo", "Error: ${error.message}")
            }
        })
    }

    private fun saveInArraysAndCheck(boardState: MutableList<MutableList<Int>>) {
        var index = 0
        val array = MutableList(9) {0}
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                val boardSaved = boardState[i][j]
                index++
                array[j] =  boardState[i][j]
                when {
                    gameBoard.checkWin(1, array) -> handleWin("X", 1)
                    gameBoard.checkWin(2, array) -> handleWin("O", 2)
                    gameBoard.isDraw(array) -> handleDraw()
                }
            }
        }
    }

    private fun updateUI(boardState: MutableList<MutableList<Int>>) {
        var index = 0
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                val button = buttonArrAll[index]
                when (boardState[i][j]) {
                    1 -> button.setBackgroundResource(R.drawable.x)
                    2 -> button.setBackgroundResource(R.drawable.o)
                    else -> button.setBackgroundResource(R.drawable.white_background)
                }
                index++
            }
        }
    }

    private fun handleWin(player: String, winCode: Int) {
        binding.TextWin.text = "Win $player"
        binding.TextWin.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        binding.TextWin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50f)
        firebaseService.setWin(winCode)
    }

    private fun handleDraw() {
        binding.TextWin.text = "Draw"
        binding.TextWin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50f)
        firebaseService.setWinSuper(0)
    }

    private fun resetGame() {
        gameBoard.resetBoardSuper()
        firebaseService.setBoardStateSuper(gameBoard.getBoardSuper())
        buttonArrAll.forEach { it.setBackgroundResource(R.drawable.white_background) }
    }
}
