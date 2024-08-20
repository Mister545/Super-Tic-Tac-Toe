// MainActivity.kt
package com.exemple.ticktacktoe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.exemple.ticktacktoe.databinding.ActivityChouseServerBinding
import com.exemple.ticktacktoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent2 = Intent(this, ChooseServerActivity::class.java)
        startActivity(intent2)

        binding.bPlaySimple.setOnClickListener {
            startActivity(intent2)


        }
        binding.bPlaySuper.setOnClickListener {
            val intent = Intent(this, SuperTicTacToe::class.java)
            startActivity(intent)
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainerView2.id, fragment)
        fragmentTransaction.commit()
    }
}
