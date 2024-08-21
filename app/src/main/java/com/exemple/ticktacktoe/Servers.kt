package com.exemple.ticktacktoe

import android.util.Log
import com.exemple.ticktacktoe.Game.FirebaseService


class Servers {
    private val firebaseService = FirebaseService()

    object ServersSimple {
        private val firebaseService = FirebaseService()

        private fun rightServer(num: Int): Boolean {
            when (num) {
                2 -> {

                    FirebasePatches.statSimpleData += "1"

                    FirebasePatches.playersNum = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.playersNumData}"
                    FirebasePatches.playersNumSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.playersNumSuperData}"
                    FirebasePatches.playersNumSuperPatch = FirebasePatches.playersNumSuperData
                    FirebasePatches.playersNumPatch = FirebasePatches.playersNumData
                    FirebasePatches.statSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}"
                    FirebasePatches.stepSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.isNextXData}"
                    FirebasePatches.stepSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.isNextXData}"
                    FirebasePatches.nextField = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.nextFieldData}"
                    FirebasePatches.nextBoard = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.prevStepData}"
                    FirebasePatches.boardStateSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.boardStateSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.data}"
                    FirebasePatches.winSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.winPositionData}"
                    FirebasePatches.exitCodeSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.exitCodeData}"
                    FirebasePatches.setupFirebaseListener = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.refSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}"
                    FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}"
                    FirebasePatches.prevStep = FirebasePatches.prevStepData

                    Log.d("ooo", "Updated statSimpleData: ${FirebasePatches.statSimpleData}")
                    Log.d("ooo", "Updated stepSimple: ${FirebasePatches.stepSimple}")

                    firebaseService.setPlayersNum(1, FirebasePatches.playersNum)
                    return false

                }
                0 -> {
                    FirebasePatches.playersNum = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.playersNumData}"
                    FirebasePatches.playersNumSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.playersNumSuperData}"
                    FirebasePatches.playersNumSuperPatch = FirebasePatches.playersNumSuperData
                    FirebasePatches.playersNumPatch = FirebasePatches.playersNumData
                    FirebasePatches.statSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}"
                    FirebasePatches.stepSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.isNextXData}"
                    FirebasePatches.stepSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.isNextXData}"
                    FirebasePatches.nextField = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.nextFieldData}"
                    FirebasePatches.nextBoard = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.prevStepData}"
                    FirebasePatches.boardStateSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.boardStateSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.data}"
                    FirebasePatches.winSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.winPositionData}"
                    FirebasePatches.exitCodeSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.exitCodeData}"
                    FirebasePatches.setupFirebaseListener = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.refSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}"
                    FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}"
                    FirebasePatches.prevStep = FirebasePatches.prevStepData

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
    object ServersSuper {
        private val firebaseService = FirebaseService()

        private fun rightServer(num: Int): Boolean {
            when (num) {
                2 -> {

                    FirebasePatches.statSuperData += "1"

                    FirebasePatches.playersNum = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.playersNumData}"
                    FirebasePatches.playersNumSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.playersNumSuperData}"
                    FirebasePatches.playersNumSuperPatch = FirebasePatches.playersNumSuperData
                    FirebasePatches.playersNumPatch = FirebasePatches.playersNumData
                    FirebasePatches.statSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}"
                    FirebasePatches.stepSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.isNextXData}"
                    FirebasePatches.stepSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.isNextXData}"
                    FirebasePatches.nextField = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.nextFieldData}"
                    FirebasePatches.nextBoard = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.prevStepData}"
                    FirebasePatches.boardStateSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.boardStateSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.data}"
                    FirebasePatches.winSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.winPositionData}"
                    FirebasePatches.exitCodeSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.exitCodeData}"
                    FirebasePatches.setupFirebaseListener = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.refSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}"
                    FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}"
                    FirebasePatches.prevStep = FirebasePatches.prevStepData


                    firebaseService.setPlayersNumSuper(1, FirebasePatches.playersNumSuper)
                    return false

                }
                0 -> {
                    FirebasePatches.playersNum = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.playersNumData}"
                    FirebasePatches.playersNumSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.playersNumSuperData}"
                    FirebasePatches.playersNumSuperPatch = FirebasePatches.playersNumSuperData
                    FirebasePatches.playersNumPatch = FirebasePatches.playersNumData
                    FirebasePatches.statSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}"
                    FirebasePatches.stepSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.isNextXData}"
                    FirebasePatches.stepSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.isNextXData}"
                    FirebasePatches.nextField = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.nextFieldData}"
                    FirebasePatches.nextBoard = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.prevStepData}"
                    FirebasePatches.boardStateSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.boardStateSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.data}"
                    FirebasePatches.winSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.winPositionData}"
                    FirebasePatches.exitCodeSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}/${FirebasePatches.exitCodeData}"
                    FirebasePatches.setupFirebaseListener = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}/${FirebasePatches.data}"
                    FirebasePatches.refSimple = "${FirebasePatches.servers}/${FirebasePatches.simple}/${FirebasePatches.statSimpleData}"
                    FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.superr}/${FirebasePatches.statSuperData}"
                    FirebasePatches.prevStep = FirebasePatches.prevStepData

                    return serverIsStarting("${FirebasePatches.statSuperData}/${FirebasePatches.playersNumSuperData}", num)
                }
                else -> return true
            }
        }
        fun serverIsStarting(patch: String, it: Int): Boolean{
            var result = false
            result = if (it == 0 || it == 1){
                ServersSuper.firebaseService.setPlayersNumSuper(it + 1, patch)
                true
            }else {
                ServersSuper.rightServer(it)
                true
            }
            return result
        }
    }
    fun waitingPlayer(patch: String, codeActivity: Int, getNum: Int, callback: (Boolean) -> Unit) {
        if (codeActivity == 0) {
            firebaseService.getPlayersNumSimple2(patch) {
                if (it == null){
                    println("ldkmv")
                }else {
                    callback(it == 2)
                }
            }
        } else {
            firebaseService.getPlayersNumSuper(patch) {
                if (it == null){
                    println("ldkmv")
                }else {
                    callback(it == 2)
                }
            }
        }
    }

}