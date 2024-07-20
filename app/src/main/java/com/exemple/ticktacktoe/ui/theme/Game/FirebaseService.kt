package com.exemple.ticktacktoe.ui.theme.Game

import android.util.Log
import com.google.firebase.database.*

class FirebaseService {

    private val database = FirebaseDatabase.getInstance()


    fun setStep(step: Boolean) {
        database.getReference("Stat").child("step").setValue(step)
    }

    fun getStep(list: MutableList<Int>) : Boolean {
        var x = 0
        var o = 0
        var y = 0

        for (value in list) {
            y += value
            if (value == 1) {
                x++
            } else if (value == 2) {
                o++
            }
        }
        val ret = when (y) {
            0 -> true
            3 -> true
            5 -> true
            6 -> true
            9 -> true
            12 -> true
            else -> false
        }
        return ret
    }

    fun setBoardState(arr: MutableList<Int>) {
        database.getReference("Stat").child("Place").setValue(arr)
    }

    fun getBoardStateAndStep(callback: (MutableList<Int>, Boolean) -> Unit) {
        val databaseReference = database.getReference("Stat/Place")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<Int>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { 0 }
                val gameStatus = getStep(boardState)

                callback(boardState, gameStatus)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(MutableList(9) { 0 }, false)

                // Логування помилки
                Log.e("ooo", "Error: ${error.message}")
            }
        })
    }


    fun setWin(win: Int) {
        database.getReference("Stat").child("win").setValue(win)
    }
}
