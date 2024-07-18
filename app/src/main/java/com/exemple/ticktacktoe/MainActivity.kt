// MainActivity.kt
package com.exemple.ticktacktoe

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.exemple.ticktacktoe.databinding.ActivityMainBinding
import com.exemple.ticktacktoe.ui.theme.Game.FirebaseService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var buttonArr: List<Button>
    private val gameBoard = GameBoard()
    private val firebaseService = FirebaseService()

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)

        // Ініціалізація зв'язування макета
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ініціалізація кнопок гри
        buttonArr = arrayListOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )

        // Виклик початкових методів
        initialization()
//        updatedList()

        // Отримання початкового кроку з БД та ініціалізація xOrO

        binding.buttonReset.setOnClickListener {
        initialization()
        }
    }

    // Початкова іниціалізація гри
    private fun initialization() {
        setupButtonListeners()
        resetGame()
    }

    // Налаштування слухачів для кнопок
    private fun setupButtonListeners() {
        for ((index, button) in buttonArr.withIndex()) {
            button.setOnClickListener {
                updateBoard(index, button)
            }
        }
    }

    // Оновлення дошки при натисканні кнопки
    private fun updateBoard(index: Int, button: Button) {
        firebaseService.getBoardState {listBD, step->
                if (listBD[index] == 0) {
                    val currentPlayer = if (step) 1 else 2
                    firebaseService.setStep(!step)
//                    gameBoard.updateBoard(index, currentPlayer)
                    button.setBackgroundResource(if (currentPlayer == 1) R.drawable.x else R.drawable.o)
                    listBD[index] = currentPlayer
                    firebaseService.setBoardState(listBD)
                    checkGameStatus()
                    gameBoard.switchPlayer()
            }
        }
    }

    // Перевірка статусу гри (перемога, нічия або продовження)
    private fun checkGameStatus() {
        when {
            gameBoard.checkWin(1) -> handleWin("X", 1)
            gameBoard.checkWin(2) -> handleWin("O", 2)
            gameBoard.isDraw() -> handleDraw()
        }
    }

    // Обробка перемоги гравця
    private fun handleWin(player: String, winCode: Int) {
        Toast.makeText(this, "Win $player", Toast.LENGTH_LONG).show()
        firebaseService.setWin(winCode)
        replaceFragment(win_fragment())
    }

    // Обробка нічиї
    private fun handleDraw() {
        Toast.makeText(this, "DRAW", Toast.LENGTH_LONG).show()
        firebaseService.setWin(0)
        replaceFragment(win_fragment())
    }

    // Скидання гри до початкового стану
    private fun resetGame() {

        firebaseService.getBoardState() { board, step ->
            gameBoard.switchPlayer()
            if (!step) gameBoard.switchPlayer()
        }

//        jdffv
        gameBoard.resetBoard() //
        firebaseService.setBoardState(gameBoard.getBoardState())
        buttonArr.forEach { it.setBackgroundResource(R.drawable.white_background) }
    }

    // Заміна поточного фрагменту на новий
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentConteiner.id, fragment)
        fragmentTransaction.commit()
    }

    // Оновлення списку з бази даних
    private fun updatedList() {
        firebaseService.getBoardState { mutable, step ->
            for ((index, button) in buttonArr.withIndex()) {
                when (mutable[index]) {
                    1 -> button.setBackgroundResource(R.drawable.x)
                    2 -> button.setBackgroundResource(R.drawable.o)
                    else -> button.setBackgroundResource(R.drawable.white_background)
                }
            }
            checkGameStatus()
        }
    }
}