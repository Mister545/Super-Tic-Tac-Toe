package com.exemple.ticktacktoe.ui.theme.Game


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

    fun setBoardState(arr: MutableList<Int>) {
        database.getReference("Stat").child("Place").setValue(arr)
    }

    fun getBoardState(callback: (MutableList<Int>) -> Unit) {
        database.getReference("Stat/Place")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val t = object : GenericTypeIndicator<MutableList<Int>>() {}
                    callback(dataSnapshot.getValue(t) ?: MutableList(9) { 0 })
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(MutableList(9) { 0 })
                }
            })
    }

    fun setWin(win: Int) {
        database.getReference("Stat").child("win").setValue(win)
    }
}
