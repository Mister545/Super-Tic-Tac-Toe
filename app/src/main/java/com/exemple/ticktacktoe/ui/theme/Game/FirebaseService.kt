package com.exemple.ticktacktoe.ui.theme.Game

import android.util.Log
import com.google.firebase.database.*

class FirebaseService {

    private val database = FirebaseDatabase.getInstance()

    fun setExitCode(code: Int){
        database.getReference("ExitCode").setValue(code)

    }
    fun setStep(step: Boolean) {
        database.getReference("StatSimple").child("isNextX").setValue(step)
    }
    fun setStepSuper(step: Boolean) {
        database.getReference("Stat").child("isNextX").setValue(step)
    }
    fun getExitCode(callback: (Int) -> Unit) {
        val databaseReference = database.getReference("ExitCode")
        databaseReference.addValueEventListener (object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val exitCode = dataSnapshot.getValue(Int::class.java)!!
                callback(exitCode)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Помилка зчитування з Firebase: ${error.message}")
            }
        })
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
    fun getStepSuper(list: MutableList<MutableList<Int>>) : Boolean {
        var y = 0
        val arrSuper = List(200) { 0 }.toMutableList()

        var index = 0
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                y += list[i][j]
                arrSuper[index] = y
                index++
            }
        }
//        Log.d("ppp", y.toString())

        val validNum = setOf(0, 3, 5, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45,
            48, 51, 54, 57, 60, 63, 66, 69, 72, 75, 78, 81, 84, 87, 90, 93, 96, 99, 102, 105, 108,
            111, 114, 117, 120, 123)
        return  y in validNum
    }

    fun setNextField(field: MutableList<Int>){
        val fieldInBd = field.filter { it != -1 }
        database.getReference("Stat").child("nextField").setValue(fieldInBd)
    }

    fun setNextBoard(nextBoard : Int){
        database.getReference("Stat/prevStep").setValue(nextBoard)
    }
    fun setBoardStateSimple(arr: MutableList<Int>) {
        database.getReference("StatSimple").child("data").setValue(arr)
    }
    fun setBoardStateSuper(arr: MutableList<MutableList<Int>>) {
        database.getReference("Stat").child("data").setValue(arr)
    }
    fun setWin(win: MutableList<Int>) {
        database.getReference("StatSimple").child("winner").setValue(win)
    }
    fun setWinSuper(win: MutableList<Int>) {
        database.getReference("Stat").child("winPosition").setValue(win)
    }
}
