package com.exemple.ticktacktoe

object FirebasePatches {

    var servers = "Servers"             ////////

    var statData = "ServerSuper"        ////////
    var data = "data"
    var exitCodeData = "ExitCode"
    var statSimpleData = "ServerSimple" ////////
    var isNextXData = "isNextX"
    var nextFieldData = "nextField"
    var prevStepData = "prevStep"
    var winPositionData = "winPosition"
    var playersNumData = "playersNum"

    var playersNum = "$servers/$statSimpleData/$playersNumData"
    var playersNumPatch = playersNumData
    var statSimple = "$servers/$statSimpleData"
    var stepSimple = "$servers/$statSimpleData/$isNextXData"
    var stepSuper = "$servers/$statData/$isNextXData"
    var nextField = "$servers/$statData/$nextFieldData"
    var nextBoard = "$servers/$statData/$prevStepData"
    var boardStateSimple = "$servers/$statSimpleData/$data"
    var boardStateSuper = "$servers/$statData/$data"
    var winSuper = "$servers/$statData/$winPositionData"
    var exitCode = exitCodeData
    var setupFirebaseListener = "$servers/$statSimpleData/$data"
    var refSimple = "$servers/$statSimpleData"
    var refSuper = "$servers/$statData"
    var prevStep = prevStepData

//    fun getData(): String {
//        return data
//    }
//
//    fun setData(value: String) {
//        data = value
//    }
//    fun getStatData(): String {
//        return statData
//    }
//
//    fun setStatSimpleData(value: String) {
//        statSimpleData = value
//    }
//    fun getStatSimpleData(): String {
//        return statSimpleData
//    }
//
//    fun setStatData(value: String) {
//        statData = value
//    }
//
//    fun setStatSimple(value: String){
//        statSimple = value
//    }
//
//    fun getStatSimple(): String{
//        return statSimple
//    }
//
//    fun getStepSimple(): String {
//        return stepSimple
//    }
//
//    fun setStepSimple(value: String) {
//        stepSimple = value
//    }
//
//    fun getStepSuper(): String {
//        return stepSuper
//    }
//
//    fun setStepSuper(value: String) {
//        stepSuper = value
//    }
//
//    fun getNextField(): String {
//        return nextField
//    }
//
//    fun setPlayersNum(value: String){
//        playersNum = value
//    }
//    fun getPlayersNum(): String {
//        return playersNum
//    }
//
//    fun setPlayersNumPatch(value: String){
//        playersNumPatch = value
//    }
//    fun getPlayersNumPatch(): String {
//        return playersNumPatch
//    }
//
//    fun setNextField(value: String) {
//        nextField = value
//    }
//
//    fun getNextBoard(): String {
//        return nextBoard
//    }
//
//    fun setNextBoard(value: String) {
//        nextBoard = value
//    }
//
//    fun setBoardStateSimple(value: String) {
//        boardStateSimple = value
//    }
//
//    fun getBoardStateSimple(): String {
//        return boardStateSimple
//    }
//
//    fun getBoardStateSuper(): String {
//        return boardStateSuper
//    }
//
//    fun setBoardStateSuper(value: String) {
//        boardStateSuper = value
//    }
//
//    fun getWinSuper(): String {
//        return winSuper
//    }
//
//    fun setWinSuper(value: String) {
//        winSuper = value
//    }
//
//    fun getExitCode(): String {
//        return exitCode
//    }
//
//    fun setExitCode(value: String) {
//        exitCode = value
//    }
//
//    fun getSetupFirebaseListener(): String {
//        return setupFirebaseListener
//    }
//
//    fun setSetupFirebaseListener(value: String) {
//        setupFirebaseListener = value
//    }
//
//    fun getRefSimple(): String {
//        return refSimple
//    }
//
//    fun setRefSimple(value: String) {
//        refSimple = value
//    }
//
//    fun getRefSuper(): String {
//        return refSuper
//    }
//
//    fun setRefSuper(value: String) {
//        refSuper = value
//    }
//
//    fun getPrevStep(): String {
//        return prevStep
//    }
//
//    fun setPrevStep(value: String) {
//        prevStep = value
//    }
}
