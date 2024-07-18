package com.exemple.ticktacktoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.exemple.ticktacktoe.databinding.FragmentWinFragmentBinding
import com.exemple.ticktacktoe.ui.theme.Game.FirebaseService
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class win_fragment : DialogFragment() {

    lateinit var binding: FragmentWinFragmentBinding
    val database = Firebase.database

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWinFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val zeroArr = MutableList(9) {0}

        getNumForWin()

        binding.bExitFragWin.setOnClickListener {
            dismiss()
        }
    }

    private fun getNumForWin(){
        val myRef = database.getReference("Stat")
        val i = myRef.child("win")
        i.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val win = dataSnapshot.getValue(Int::class.java)
                initialisationWin(win)
            }

            override fun onCancelled(error: DatabaseError) {
                // Обробка помилок
                println("Помилка зчитування з Firebase: ${error.message}")
            }
        })
    }
    private fun initialisationWin(i: Int?){

        binding.tWinFrag.text = when (i) {
            1 -> "win X"
            2 -> "win O"
            else -> "Draw"
        }
    }
}

