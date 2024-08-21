// MainActivity.kt
package com.exemple.ticktacktoe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.exemple.ticktacktoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
}
