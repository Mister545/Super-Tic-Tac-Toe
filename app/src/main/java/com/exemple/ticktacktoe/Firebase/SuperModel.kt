package com.exemple.ticktacktoe.Firebase

import androidx.compose.foundation.layout.R

class SuperModel {
    data class ServerSimple(
        var ExitCode: Int? = null,
        var data: MutableList<Int>? = null,
        var isNextX: Boolean? = null,
        var playersNum: Int? = null
    )

    data class Simple(
        var serverSimple: ServerSimple? = null
    )

    data class Super(
        var serverSimple: ServerSuper? = null
    )

    data class Room(
        var serverSimple: ServerSimple? = null
    )

    data class ServerSuper(
        var ExitCode: Int? = null,
        var data: MutableList<MutableList<Int>>? = null,
        var isNextX: Boolean? = null,
        var nextField: MutableList<Int>? = null,
        var playersNum: Int? = null,
        var prevStep: Int? = null,
        var winPosition: MutableList<Int>? = null
    )

    data class Rooms(
        var simple: Map<String, ServerSimple>? = mapOf(),
        var superr: Map<String, ServerSuper>? = mapOf()
    )

    data class Servers(
        var rooms: Map<String, Rooms> = mapOf(),
        var simple: Map<String, ServerSimple> = mapOf(),
        var superr: Map<String, ServerSuper> = mapOf()
    )
}
