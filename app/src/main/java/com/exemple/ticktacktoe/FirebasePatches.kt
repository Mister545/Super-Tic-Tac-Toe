package com.exemple.ticktacktoe

object FirebasePatches {

    var servers = ""             ////////

    var statData = "StatSuper"        ////////
    var data = "data"
    var exitCodeData = "ExitCode"
    var statSimpleData = "StatSimple" ////////
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
    var exitCode = "$servers/$statData/$exitCodeData"
    var setupFirebaseListener = "$servers/$statSimpleData/$data"
    var refSimple = "$servers/$statSimpleData"
    var refSuper = "$servers/$statData"
    var prevStep = prevStepData
}
