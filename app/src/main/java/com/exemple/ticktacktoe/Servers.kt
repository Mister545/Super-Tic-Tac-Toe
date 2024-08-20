package com.exemple.ticktacktoe

import android.util.Log
import com.exemple.ticktacktoe.Game.FirebaseService


class Servers {
    object ServersSimple {
        private val firebaseService = FirebaseService()

        private fun rightServer(num: Int): Boolean {
            when (num) {
                2 -> {

                    FirebasePatches.statSimpleData += "1"

                    FirebasePatches.playersNum =
                        "${FirebasePatches.statSimpleData}/${FirebasePatches.playersNumData}"
                    FirebasePatches.stepSimple =
                        "${FirebasePatches.statSimpleData}/${FirebasePatches.isNextXData}"
                    FirebasePatches.boardStateSimple =
                        "${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.setupFirebaseListener =
                        "${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.refSimple = FirebasePatches.statSimpleData

                    Log.d("ooo", "Updated statSimpleData: ${FirebasePatches.statSimpleData}")
                    Log.d("ooo", "Updated stepSimple: ${FirebasePatches.stepSimple}")

                    firebaseService.setPlayersNum(1, FirebasePatches.playersNum)
                    return false

                }
                0 -> {
                    FirebasePatches.playersNum =
                        "${FirebasePatches.statSimpleData}/${FirebasePatches.playersNumData}"
                    FirebasePatches.stepSimple =
                        "${FirebasePatches.statSimpleData}/${FirebasePatches.isNextXData}"
                    FirebasePatches.boardStateSimple =
                        "${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.setupFirebaseListener =
                        "${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.refSimple = FirebasePatches.statSimpleData

                    return serverIsStarting("${FirebasePatches.statSimpleData}/${FirebasePatches.playersNumData}", num)
                }
                else -> return true
            }
        }

        fun serverIsStarting(patch: String, it: Int): Boolean{
            var result = false
                result = if (it == 0 || it == 1){
                    firebaseService.setPlayersNum(it + 1, patch)
                    true
                }else {
                    rightServer(it)
                    true
                }
            return result
        }
    }
}