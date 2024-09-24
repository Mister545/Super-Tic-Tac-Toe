package com.exemple.ticktacktoe.Firebase

import android.util.Log
import com.google.firebase.database.*

class FirebaseService {
//    private val constFirebasePackages = constFirebasePackages()
//    var firebaseListener: ValueEventListener? = null
    private val database = FirebaseDatabase.getInstance()

    fun removeListener(path: String, listener: ValueEventListener) {
        val databaseReference = database.getReference(path)
        databaseReference.removeEventListener(listener)
}

    fun setUserName(path: String, userUid: String, name: String?){
        database.getReference(path).setValue(userUid)
        database.getReference(path).child(userUid).child("name").setValue(name)
    }

    fun getPlayersNumSimple2( path: String, callback: (Int) -> Unit) {
        val databaseReference = database.getReference(path)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue(Int::class.java) == null){
                    println()
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
    fun getPlayerName( path: String, callback: (String) -> Unit) {
        val databaseReference = database.getReference(path)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue(String::class.java) == null){
                    println()
                }else {
                    val playerName = dataSnapshot.getValue(String::class.java)!!
                    Log.d("ooo", "playerName $playerName")
                    callback(playerName)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Помилка зчитування з Firebase: ${error.message}")
            }
        })
    }
    fun getCodeRoom( path: String, callback: (Int, String) -> Unit) {
        val databaseReference = database.getReference(path)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result = dataSnapshot.value as? Map<String, Map<String, *>>  // Отримуємо карту з типами String
                if (result == null) {
                    callback(0, "0")
                } else {
                    result.forEach { (key, data) ->
                        val serverSimpleName =
                            data.keys.firstOrNull() // Отримуємо перший ключ, який може бути назвою ServerSimple

                        callback(key.toInt(), serverSimpleName?.toString()!!)
                    }
                }
                Log.d("ooo", "codeRoom: ${dataSnapshot.value}")
            }

            override fun onCancelled(error: DatabaseError) {
                println("Помилка зчитування з Firebase: ${error.message}")
            }
        })
    }


    fun setPlayersNum(playersNum: Int, path: String){
        database.getReference(path).setValue(playersNum)
    }
    fun setPlayersNumSuper(playersNum: Int, path: String){
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
fun getExitCode(path: String, callback: (Int) -> Unit): ValueEventListener {
    val firebaseListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val exitCode = dataSnapshot.getValue(Int::class.java)
            if (exitCode != null) {
                callback(exitCode)
            } else {
                // Обробка випадку, коли дані відсутні або є null
                println("Дані відсутні або є null")
            }        }

        override fun onCancelled(error: DatabaseError) {
            println("Помилка зчитування з Firebase: ${error.message}")
        }
    }

    val databaseReference = database.getReference(path)
    databaseReference.addValueEventListener(firebaseListener)

    return firebaseListener
}

    fun getPlayersNumSuper( path: String, callback: (Int) -> Unit) {
        val databaseReference = database.getReference(path)
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue(Int::class.java) == null){
                    setPlayersNumSuper(0, FirebasePatches.playersNumSuper)
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

    fun getListServers(typeGame: String, callback: (MutableList<Any>) -> Unit) {

        var newPath = ""
        val model: Any

        when (typeGame) {
            "Super" -> {
                newPath = "Servers/super"
                model = SuperModel.ServerSuper()
            }
            "RoomSuper" -> {
                newPath = "Servers/rooms/superr"
                model = SuperModel.ServerSuper()
            }
            "Simple" -> {
                newPath = "Servers/simple"
                model = SuperModel.ServerSimple()
            }
            "RoomSimple" -> {
                newPath = "Servers/rooms/simple"
                model = SuperModel.ServerSimple()
            }
            else -> model = SuperModel.ServerSuper()
        }

        Log.d("ooo", "modellllllll $model")
        val databaseReference = database.getReference(newPath)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val serverList = mutableListOf<Any>()
                for (serverSnapshot in snapshot.children) {
                    val server = serverSnapshot.getValue(model::class.java)
                    server?.let {
                        serverList.add(it)
                    }
                    callback(serverList)
                }

                Log.d("ooo", "Отримані дані: $serverList")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseData", "Помилка зчитування: ${error.message}")
            }
        })
    }

    fun getPlayersNumSimpleEvent(path: String, callback: (Int) -> Unit): ValueEventListener {
        val firebaseListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val playerNum = dataSnapshot.getValue(Int::class.java)
                if (playerNum == null) {
//                    setPlayersNum(0, FirebasePatches.playersNum)
                }else
                callback(playerNum)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Помилка зчитування з Firebase: ${error.message}")
            }
        }


        val databaseReferenceForNumSimple = database.getReference(path)
        databaseReferenceForNumSimple.addValueEventListener(firebaseListener)

        return firebaseListener
    }
    fun getPlayersNumSuperEvent(path: String, callback: (Int) -> Unit): ValueEventListener {
        val firebaseListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val playerNum = dataSnapshot.getValue(Int::class.java)
                if (playerNum == null) {
//                    setPlayersNum(0, FirebasePatches.playersNum)
                }else
                callback(playerNum)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Помилка зчитування з Firebase: ${error.message}")
            }
        }


        val databaseReferenceForNumSuper = database.getReference(path)
        databaseReferenceForNumSuper.addValueEventListener(firebaseListener)

        return firebaseListener
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

    fun getAllBase(callback: (SuperModel.Servers) -> Unit) {
        val databaseReference = database.getReference("Servers")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val servers = dataSnapshot.getValue(SuperModel.Servers::class.java)
                Log.d("ooo", servers.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ooo", "Error: ${error.message}")
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
        val validNum = setOf(0, 3, 5, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45,
            48, 51, 54, 57, 60, 63, 66, 69, 72, 75, 78, 81, 84, 87, 90, 93, 96, 99, 102, 105, 108,
            111, 114, 117, 120, 123)
        return  y in validNum
    }
}
