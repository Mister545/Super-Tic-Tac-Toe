package com.exemple.ticktacktoe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.exemple.ticktacktoe.databinding.FragmentSuperTicTacToeBinding
import com.exemple.ticktacktoe.ui.theme.Game.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener


class SuperTicTacToe : DialogFragment() {

    private lateinit var binding: FragmentSuperTicTacToeBinding
    private lateinit var buttonArrWithArr: List<List<Button>>
    private lateinit var buttonArrAll: List<Button>
    private val database = FirebaseDatabase.getInstance()
    private val firebaseService = FirebaseService()
    private val gameBoard = GameBoard()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuperTicTacToeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseService.setNextBoard(10)
        setupButtons()
        initialization()

        binding.resetSuper.setOnClickListener{
            firebaseService.setNextBoard(10)
            setupButtons()
            initialization()
        }
    }

    private fun setupButtons() {
        buttonArrWithArr = listOf(
            listOf(binding.button000000, binding.button000101, binding.button000202, binding.button000303, binding.button000404, binding.button000505, binding.button000606, binding.button000707, binding.button000808),
            listOf(binding.button010009, binding.button010110, binding.button010211, binding.button010312, binding.button010413, binding.button010514, binding.button010615, binding.button010716, binding.button010817),
            listOf(binding.button020018, binding.button020119, binding.button020220, binding.button020321, binding.button020422, binding.button020523, binding.button020624, binding.button020725, binding.button020826),
            listOf(binding.button030027, binding.button030128, binding.button030229, binding.button030330, binding.button030431, binding.button030532, binding.button030633, binding.button030734, binding.button030835),
            listOf(binding.button040036, binding.button040137, binding.button040238, binding.button040339, binding.button040440, binding.button040541, binding.button040642, binding.button040743, binding.button040844),
            listOf(binding.button050045, binding.button050146, binding.button050247, binding.button050348, binding.button050449, binding.button050550, binding.button050651, binding.button050752, binding.button050853),
            listOf(binding.button060054, binding.button060155, binding.button060256, binding.button060357, binding.button060458, binding.button060559, binding.button060660, binding.button060761, binding.button060862),
            listOf(binding.button070063, binding.button070164, binding.button070265, binding.button070366, binding.button070467, binding.button070568, binding.button070669, binding.button070770, binding.button070871),
            listOf(binding.button080072, binding.button080173, binding.button080274, binding.button080375, binding.button080476, binding.button080577, binding.button080678, binding.button080779, binding.button080880)
        )
        buttonArrAll = buttonArrWithArr.flatten()
    }

    private fun initialization() {
        setupButtonListeners()
        resetGame()
        setupFirebaseListenerAndChecker()
    }
    private fun setStoke(board: MutableList<MutableList<Int>>) {
        firebaseService.getNextBoard { nextBoardIndex ->
            if (nextBoardIndex == 10) {
                board.forEachIndexed { index, i ->
                    for (j in nextField(nextBoardIndex, gameBoard.getWinListSuper())) {
                        Log.d("ooo", "jjjjjjjjj ======== $j")
                        gameBoard.setStrokeOnButtonGreen(
                            buttonArrWithArr[j][index],
                            requireContext()
                        )
                    }
                }
            } else {
                for (i in board.indices) {
                    for (j in board[i].indices) {
                        gameBoard.setStrokeOnButtonBlack(buttonArrWithArr[i][j], requireContext())
                    }
                }
                for (i in board[nextBoardIndex].indices) {
                    gameBoard.setStrokeOnButtonGreen(
                        buttonArrWithArr[nextBoardIndex][i],
                        requireContext()
                    )
                }
            }
        }
    }


    private fun setupButtonListeners() {
        firebaseService.getNextBoard { nextBoard ->
            disableAllButtons()
            firebaseService.setNextField(nextField(nextBoard, gameBoard.getWinListSuper()))
            if(gameBoard.checkRightPlace(nextBoard)){
                disableBoardWinner(nextBoard)
                firebaseService.setNextBoard(10)
                buttonArrWithArr.forEachIndexed { i, _ -> enableBoardButtons(i) }
            }
            else if (nextBoard == 10) {
                buttonArrWithArr.forEachIndexed { i, _ -> enableBoardButtons(i) }
            } else {
                enableBoardButtons(nextBoard)
            }
        }
    }
    private fun nextField(nextBoard: Int, winListSuper: MutableList<Int>):MutableList<Int>{
        val arr= mutableListOf(0,1,2,3,4,5,6,7,8)

        return if (nextBoard == 10 || prevStepInWinListSuper(nextBoard, winListSuper)) {
            winListSuper.forEachIndexed { index, i ->
                if (i != 0)
                    arr.removeAt(index)
            }
            arr
        }else {
            mutableListOf(nextBoard)
        }
    }
    private fun prevStepInWinListSuper(nextBoard: Int, winListSuper: MutableList<Int>) : Boolean{
        winListSuper.forEachIndexed { index, i ->
            return i != 0 && index == nextBoard
        }
        return false
    }

    private fun disableAllButtons() {
        buttonArrWithArr.flatten().forEach { it.isClickable = false }
    }
    private fun disableBoardWinner(i : Int) {
        buttonArrWithArr[i].forEach { it.isClickable = false }
    }

    private fun enableBoardButtons(boardIndex: Int) {
        buttonArrWithArr[boardIndex].forEachIndexed { index, button ->
            button.isClickable = true
            if(gameBoard.checkRightPlace(boardIndex)) {
                Log.d("ooo", "chacking======${gameBoard.checkRightPlace(boardIndex)}")
                disableBoardWinner(boardIndex)
                firebaseService.setNextBoard(10)
            } else {
                button.setOnClickListener {
                    updateBoard(boardIndex, index, button)
                    firebaseService.setNextBoard(index)
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
                button.setBackgroundResource(if (step) R.drawable.x else R.drawable.o)
                firebaseService.setBoardStateSuper(listBD)
            }
        }
    }

    private fun getBoardState(callback: (MutableList<MutableList<Int>>, Boolean) -> Unit) {
        val databaseReference = database.getReference("Stat/data")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<MutableList<Int>>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { MutableList(9) { 0 } }
                val gameStatus = firebaseService.getStepSuper(boardState)
                setStoke(boardState)
                callback(boardState, gameStatus)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooo", "Error: ${error.message}")
                callback(MutableList(9) { MutableList(9) { 0 } }, false)
            }
        })
    }

    private fun setupFirebaseListenerAndChecker() {
        val databaseReference = database.getReference("Stat/data")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<MutableList<Int>>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { MutableList(9) {0}}
                updateUI(boardState)
                gameBoard.checkWinSuper(boardState, binding)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooo", "Error: ${error.message}")
            }
        })
    }

    private fun updateUI(boardState: MutableList<MutableList<Int>>) {
        boardState.flatten().forEachIndexed { index, value ->
            val button = buttonArrAll[index]
            when (value) {
                1 -> button.setBackgroundResource(R.drawable.x)
                2 -> button.setBackgroundResource(R.drawable.o)
                else -> gameBoard.setBackgroundButtonsSuper(button)
            }
        }
    }

    private fun resetGame() {
        gameBoard.resetBoardSuper()
        firebaseService.setBoardStateSuper(gameBoard.getBoardSuper())
        buttonArrAll.forEach { gameBoard.setBackgroundButtonsSuper(it)}
    }

//    private fun handleWin(player: String, winCode: Int) {
//        binding.TextWin.text = "Win $player"
//        binding.TextWin.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
//        binding.TextWin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50f)
//        firebaseService.setWin(winCode)
//    }

//    private fun handleDraw() {
//        binding.TextWin.text = "Draw"
//        binding.TextWin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50f)
//        firebaseService.setWinSuper(0)
//    }
}
