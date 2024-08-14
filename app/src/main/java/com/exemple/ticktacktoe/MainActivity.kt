// MainActivity.kt
package com.exemple.ticktacktoe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import com.exemple.ticktacktoe.databinding.ActivityMainBinding
import com.exemple.ticktacktoe.ui.theme.Game.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val firebaseService = FirebaseService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bPlaySimple.setOnClickListener {
            val intent = Intent(this, SimpleTicTacToe::class.java)
            startActivity(intent)
        }
        binding.bPlaySuper.setOnClickListener {
            val intent = Intent(this, SuperTicTacToe::class.java)
            startActivity(intent)
        }
    }

    fun handleWin(player: String, winCode: Int) {
        Toast.makeText(this, "Win $player", Toast.LENGTH_LONG).show()
        firebaseService.setWin(winCode)
    }
    private fun handleDraw() {
        Toast.makeText(this, "DRAW", Toast.LENGTH_LONG).show()
        firebaseService.setWin(0)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentConteinerSimple.id, fragment)
        fragmentTransaction.commit()
        binding.bPlaySimple.visibility = View.GONE
        binding.TicTacToeText.visibility = View.GONE
        binding.bPlaySuper.visibility = View.GONE
        binding.fragmentConteinerSimple.visibility = View.VISIBLE
    }
    private fun replaceFragmentSuper(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentConteinerSuper.id, fragment)
        fragmentTransaction.commit()
        binding.bPlaySimple.visibility = View.GONE
        binding.TicTacToeText.visibility = View.GONE
        binding.bPlaySuper.visibility = View.GONE
        binding.fragmentConteinerSuper.visibility = View.VISIBLE
    }
}
