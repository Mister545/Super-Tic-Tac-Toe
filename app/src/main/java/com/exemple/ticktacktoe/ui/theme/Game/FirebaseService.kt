package com.exemple.ticktacktoe.ui.theme.Game


import android.util.Log
import com.exemple.ticktacktoe.GameBoard
import com.google.android.gms.dynamic.IFragmentWrapper
import com.google.firebase.database.*

class FirebaseService {

    private val database = FirebaseDatabase.getInstance()

    fun setStepX() {
        database.getReference("Stat").child("step").setValue(true)
    }

    fun setStep(step: Boolean) {
        database.getReference("Stat").child("step").setValue(step)
    }

    fun getStep(callback: (Boolean) -> Unit) {
        database.getReference("Stat").child("step")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    callback(dataSnapshot.getValue(Boolean::class.java) ?: true)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(true)
                }
            })
    }
    fun step(list: MutableList<Int>) : Boolean {
        Log.d("ooo", "list==========$list")
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
        val ret = when {
            y == 0 -> true
            y == 3 -> true
            y == 5 -> true
            y == 6 -> true
            y == 9 -> true
            y == 12 -> true
            else -> false
        }
        return ret
    }

    fun setBoardState(arr: MutableList<Int>) {
        database.getReference("Stat").child("Place").setValue(arr)
    }

    fun getBoardState(callback: (MutableList<Int>, Boolean) -> Unit) {
        val databaseReference = database.getReference("Stat/Place")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val t = object : GenericTypeIndicator<MutableList<Int>>() {}
                val boardState = dataSnapshot.getValue(t) ?: MutableList(9) { 0 }
                val gameStatus = step(boardState)

                callback(boardState, gameStatus)

                // Додайте логування
//                Log.d("ooo", "Data received: $boardState")
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
