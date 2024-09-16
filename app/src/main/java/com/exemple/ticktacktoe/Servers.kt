package com.exemple.ticktacktoe

import android.util.Log
import com.exemple.ticktacktoe.Game.FirebaseService
import kotlin.random.Random


class Servers {
    private val firebaseService = FirebaseService()

    fun signInRoom(code: Int, callback: (Boolean, String) -> Unit) {

        var servers = "Servers"             ////////
        val room = "rooms"
        val codeRoom = code

        val signInRoom = "$servers/$room/$codeRoom"
        val signRoomCode = "$servers/$room"

        FirebasePatches.signInRoom = "$servers/$room/$codeRoom"

        FirebasePatches.servers = signInRoom
        servers = signInRoom
        val rooms = "$servers/$room"

        val statSuperData = "ServerSuper"
        val statSimpleData = "ServerSimple"////////
        val superr = "super"////////
        val simple= "simple"////////
        val data = "data"
        val exitCodeData = "ExitCode"
        val isNextXData = "isNextX"
        val nextFieldData = "nextField"
        val prevStepData = "prevStep"
        val winPositionData = "winPosition"
        val playersNumData = "playersNum"
        val playersNumSuperData = "playersNum"

        FirebasePatches.playersNum = "$servers/$statSimpleData/$playersNumData"
        FirebasePatches.playersNumSuper = "$servers/$statSuperData/$playersNumSuperData"
        FirebasePatches.playersNumSuperPatch = playersNumSuperData
        FirebasePatches.playersNumPatch = playersNumData
        FirebasePatches.statSimple = "$servers/$statSimpleData"
        FirebasePatches.stepSimple = "$servers/$statSimpleData/$isNextXData"
        FirebasePatches.stepSuper = "$servers/$statSuperData/$isNextXData"
        FirebasePatches.nextField = "$servers/$statSuperData/$nextFieldData"
        FirebasePatches.nextBoard = "$servers/$statSuperData/$prevStepData"
        FirebasePatches.boardStateSimple = "$servers/$statSimpleData/$data"
        FirebasePatches.boardStateSuper = "$servers/$statSuperData/$data"
        FirebasePatches.winSuper = "$servers/$statSuperData/$winPositionData"
        FirebasePatches.exitCodeSuper = "$servers/$statSuperData/$exitCodeData"
        FirebasePatches.exitCodeSimple = "$servers/$statSimpleData/$exitCodeData"
        FirebasePatches.setupFirebaseListener = "$servers/$statSimpleData/$data"
        FirebasePatches.refSimple = "$servers/$statSimpleData"
        FirebasePatches.refSuper = "$servers/$statSuperData"
        FirebasePatches.prevStep = prevStepData


        fun verifyCode(callback: (Boolean, String) -> Unit){
            firebaseService.getCodeRoom(signRoomCode){ it, type->
                if (it == 0) {
                    callback(false, "_")
                }else{
                    if (code == it)
                        callback(true, type)
                    else
                        callback(false, type)
                }
            }
        }
        verifyCode {it, type->
            callback(it, type)
        }
    }
    fun createRoom(random: Int) {

        var servers = "Servers"             ////////
        val room = "rooms"

        val rooms = "$servers/$room"

        FirebasePatches.servers = rooms
        servers = rooms

        val statSuperData = "ServerSuper"
        val statSimpleData = "ServerSimple"////////
        val superr = random////////
        val simple= random ////////
        val data = "data"
        val exitCodeData = "ExitCode"
        val isNextXData = "isNextX"
        val nextFieldData = "nextField"
        val prevStepData = "prevStep"
        val winPositionData = "winPosition"
        val playersNumData = "playersNum"
        val playersNumSuperData = "playersNum"

        FirebasePatches.playersNum = "$servers/$simple/$statSimpleData/$playersNumData"
        FirebasePatches.playersNumSuper = "$servers/$superr/$statSuperData/$playersNumSuperData"
        FirebasePatches.playersNumSuperPatch = playersNumSuperData
        FirebasePatches.playersNumPatch = playersNumData
        FirebasePatches.statSimple = "$servers/$simple/$statSimpleData"
        FirebasePatches.stepSimple = "$servers/$simple/$statSimpleData/$isNextXData"
        FirebasePatches.stepSuper = "$servers/$superr/$statSuperData/$isNextXData"
        FirebasePatches.nextField = "$servers/$superr/$statSuperData/$nextFieldData"
        FirebasePatches.nextBoard = "$servers/$superr/$statSuperData/$prevStepData"
        FirebasePatches.boardStateSimple = "$servers/$simple/$statSimpleData/$data"
        FirebasePatches.boardStateSuper = "$servers/$superr/$statSuperData/$data"
        FirebasePatches.winSuper = "$servers/$superr/$statSuperData/$winPositionData"
        FirebasePatches.exitCodeSuper = "$servers/$superr/$statSuperData/$exitCodeData"
        FirebasePatches.exitCodeSimple = "$servers/$simple/$statSimpleData/$exitCodeData"
        FirebasePatches.setupFirebaseListener = "$servers/$simple/$statSimpleData/$data"
        FirebasePatches.refSimple = "$servers/$simple/$statSimpleData"
        FirebasePatches.refSuper = "$servers/$superr/$statSuperData"
        FirebasePatches.prevStep = prevStepData
    }
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
                callback(it == 2)
            }
        } else {
            firebaseService.getPlayersNumSuper(patch) {
                callback(it == 2)
            }
        }
    }
}