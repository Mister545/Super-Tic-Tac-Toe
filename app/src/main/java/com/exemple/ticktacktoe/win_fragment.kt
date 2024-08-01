package com.exemple.ticktacktoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.exemple.ticktacktoe.databinding.WinFragmentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class win_fragment : DialogFragment() {

    private lateinit var binding: WinFragmentBinding
    private val database = Firebase.database

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WinFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNumForWin()
        binding.bExitFragWin.setOnClickListener { dismiss() }
    }

    private fun getNumForWin() {
        val myRef = database.getReference("StatSimple")
        myRef.child("winner").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val win = dataSnapshot.getValue(Int::class.java)
                initialisationWin(win)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Помилка зчитування з Firebase: ${error.message}")
            }
        })
    }

    private fun initialisationWin(winCode: Int?) {
        binding.tWinFrag.text = when (winCode) {
            1 -> "win X"
            2 -> "win O"
            else -> "Draw"
        }
    }
}
