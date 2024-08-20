//package com.exemple.ticktacktoe.ui.theme.Game
//
//import android.widget.Button
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import com.exemple.ticktacktoe.R
//import com.exemple.ticktacktoe.win_fragment
//
//class GameRules {
//    private val gameBoard = GameBoard()
//    private val firebaseService = FirebaseService()
//    private lateinit var buttonArr: List<Button>
//
//    buttonArr = arrayListOf(
//    R.id.button1, R.id.button2,R.id.button3,
//    R.id.button4,R.id.button5, R.id.button6,
//    R.id.button7,R.id.button8, R.id.button9
//    )
//    // Перевірка статусу гри (перемога, нічия або продовження)
//    fun checkGameStatus() {
//
//        when {
//            gameBoard.checkWin(1) -> handleWin("X", 1)
//            gameBoard.checkWin(2) -> handleWin("O", 2)
//            gameBoard.isDraw() -> handleDraw()
//        }
//    }
//
//    // Обробка перемоги гравця
//    private fun handleWin(player: String, winCode: Int) {
//        Toast.makeText(this, "Win $player", Toast.LENGTH_LONG).show()
//        firebaseService.setWin(winCode)
//        replaceFragment(win_fragment())
//        resetGame()
//    }
//
//    // Обробка нічиї
//    private fun handleDraw() {
//        Toast.makeText(this, "DRAW", Toast.LENGTH_LONG).show()
//        firebaseService.setWin(0)
//        replaceFragment(win_fragment())
//        resetGame()
//    }
//
//    // Скидання гри до початкового стану
//    private fun resetGame() {
//        gameBoard.resetBoard()
//        firebaseService.setBoardState(gameBoard.getBoardState())
//        buttonArr.forEach { it.setBackgroundResource(R.drawable.white_background) }
//    }
//
//    // Заміна поточного фрагменту на новий
//    private fun replaceFragment(fragment: Fragment) {
//        val fragmentManager = supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(binding.fragmentConteiner.id, fragment)
//        fragmentTransaction.commit()
//    }
//
//
//}