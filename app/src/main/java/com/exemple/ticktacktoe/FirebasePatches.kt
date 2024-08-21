package com.exemple.ticktacktoe

object FirebasePatches {

    var servers = "Servers"             ////////

    var statSuperData = "ServerSuper"
    var statSimpleData = "ServerSimple"////////
    var superr = "super"////////
    var simple= "simple"////////
    var data = "data"
    var exitCodeData = "ExitCode"
    var isNextXData = "isNextX"
    var nextFieldData = "nextField"
    var prevStepData = "prevStep"
    var winPositionData = "winPosition"
    var playersNumData = "playersNum"
    var playersNumSuperData = "playersNum"

    var playersNum = "$servers/$simple/$statSimpleData/$playersNumData"
    var playersNumSuper = "$servers/$superr/$statSuperData/$playersNumSuperData"
    var playersNumSuperPatch = playersNumSuperData
    var playersNumPatch = playersNumData
    var statSimple = "$servers/$simple/$statSimpleData"
    var stepSimple = "$servers/$simple/$statSimpleData/$isNextXData"
    var stepSuper = "$servers/$superr/$statSuperData/$isNextXData"
    var nextField = "$servers/$superr/$statSuperData/$nextFieldData"
    var nextBoard = "$servers/$superr/$statSuperData/$prevStepData"
    var boardStateSimple = "$servers/$simple/$statSimpleData/$data"
    var boardStateSuper = "$servers/$superr/$statSuperData/$data"
    var winSuper = "$servers/$superr/$statSuperData/$winPositionData"
    var exitCodeSuper = "$servers/$superr/$statSuperData/$exitCodeData"
    var exitCodeSimple = "$servers/$simple/$statSimpleData/$exitCodeData"
    var setupFirebaseListener = "$servers/$simple/$statSimpleData/$data"
    var refSimple = "$servers/$simple/$statSimpleData"
    var refSuper = "$servers/$superr/$statSuperData"
    var prevStep = prevStepData
}
