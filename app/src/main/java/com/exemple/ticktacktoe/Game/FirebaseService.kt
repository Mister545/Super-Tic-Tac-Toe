package com.exemple.ticktacktoe.Game

import com.exemple.ticktacktoe.FirebasePatches
import com.google.firebase.database.*

class FirebaseService {
//    private val constFirebasePackages = constFirebasePackages()
    var firebaseListener: ValueEventListener? = null

    private val database = FirebaseDatabase.getInstance()

    fun setPlayersNum(playersNum: Int, path: String){
        database.getReference(path).setValue(playersNum)
    }
    fun setExitCode(code: Int, path: String){
        database.getReference(path).setValue(code)
    }
    fun setStep(step: Boolean, path: String) {
        database.getReference(path).setValue(step)
    }
    fun setStepSuper(step: Boolean, path: String) {
        database.getReference(path).setValue(step)
    }
    fun setNextField(field: MutableList<Int>, path: String){
        val fieldInBd = field.filter { it != -1 }
        database.getReference(path).setValue(fieldInBd)
    }

    fun setNextBoard(nextBoard : Int, path: String){
        database.getReference(path).setValue(nextBoard)
    }
    fun setBoardStateSimple(arr: MutableList<Int>, path: String) {
        database.getReference(path).setValue(arr)
    }
    fun setBoardStateSuper(arr: MutableList<MutableList<Int>>, path: String) {
        database.getReference(path).setValue(arr)
    }
    fun setWinSuper(win: MutableList<Int>, path: String) {
        database.getReference(path).setValue(win)
    }
    fun getExitCode( path: String, callback: (Int) -> Unit) {
        val databaseReference = database.getReference(path)
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
    fun getPlayersNumSimple( path: String, callback: (Int) -> Unit) {
        val databaseReference = database.getReference(path)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue(Int::class.java) == null){
                    setPlayersNum(0, FirebasePatches.playersNum)
                    callback(0)
                }else {
                    val playerNum = dataSnapshot.getValue(Int::class.java)!!
                    callback(playerNum)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Помилка зчитування з Firebase: ${error.message}")
            }
        })
    }
    fun getPlayersNumSimpleEvent( path: String, callback: (Int) -> Unit) {
        val databaseReferenceForNumSimple = database.getReference(path)
        firebaseListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue(Int::class.java) == null){
                    setPlayersNum(0, FirebasePatches.playersNum)
                    callback(0)
                }else {
                    val playerNum = dataSnapshot.getValue(Int::class.java)!!
                    callback(playerNum)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Помилка зчитування з Firebase: ${error.message}")
            }
        }
        databaseReferenceForNumSimple.addValueEventListener(firebaseListener!!)
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
        val validNum = setOf(0, 3, 5, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45,
            48, 51, 54, 57, 60, 63, 66, 69, 72, 75, 78, 81, 84, 87, 90, 93, 96, 99, 102, 105, 108,
            111, 114, 117, 120, 123)
        return  y in validNum
    }
}
