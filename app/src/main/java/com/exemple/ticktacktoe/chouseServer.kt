package com.exemple.ticktacktoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exemple.ticktacktoe.databinding.ActivityChouseServerBinding

class ChooseServerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChouseServerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChouseServerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button0.setOnClickListener {
            FirebasePatches.statData += "0"

            FirebasePatches.stepSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.isNextXData}"
            FirebasePatches.nextField =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.nextFieldData}"
            FirebasePatches.nextBoard =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.prevStepData}"
            FirebasePatches.boardStateSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.data}"
            FirebasePatches.winSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.winPositionData}"
            FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.statData}"
            FirebasePatches.exitCode = "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.exitCodeData}"

            finish()
        }
        binding.button1.setOnClickListener {
            FirebasePatches.statData += "1"

            FirebasePatches.stepSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.isNextXData}"
            FirebasePatches.nextField =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.nextFieldData}"
            FirebasePatches.nextBoard =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.prevStepData}"
            FirebasePatches.boardStateSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.data}"
            FirebasePatches.winSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.winPositionData}"
            FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.statData}"
            FirebasePatches.exitCode = "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.exitCodeData}"

            finish()
        }
        binding.button2.setOnClickListener {
            FirebasePatches.statData += "2"

            FirebasePatches.stepSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.isNextXData}"
            FirebasePatches.nextField =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.nextFieldData}"
            FirebasePatches.nextBoard =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.prevStepData}"
            FirebasePatches.boardStateSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.data}"
            FirebasePatches.winSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.winPositionData}"
            FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.statData}"
            FirebasePatches.exitCode = "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.exitCodeData}"

            finish()
        }
        binding.button3.setOnClickListener {
            FirebasePatches.statData += "3"

            FirebasePatches.stepSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.isNextXData}"
            FirebasePatches.nextField =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.nextFieldData}"
            FirebasePatches.nextBoard =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.prevStepData}"
            FirebasePatches.boardStateSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.data}"
            FirebasePatches.winSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.winPositionData}"
            FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.statData}"
            FirebasePatches.exitCode = "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.exitCodeData}"

            finish()
        }
        binding.button4.setOnClickListener {
            FirebasePatches.statData += "4"

            FirebasePatches.stepSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.isNextXData}"
            FirebasePatches.nextField =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.nextFieldData}"
            FirebasePatches.nextBoard =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.prevStepData}"
            FirebasePatches.boardStateSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.data}"
            FirebasePatches.winSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.winPositionData}"
            FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.statData}"
            FirebasePatches.exitCode = "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.exitCodeData}"

            finish()
        }
        binding.button5.setOnClickListener {
            FirebasePatches.statData += "5"

            FirebasePatches.stepSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.isNextXData}"
            FirebasePatches.nextField =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.nextFieldData}"
            FirebasePatches.nextBoard =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.prevStepData}"
            FirebasePatches.boardStateSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.data}"
            FirebasePatches.winSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.winPositionData}"
            FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.statData}"
            FirebasePatches.exitCode = "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.exitCodeData}"

            finish()
        }
        binding.button6.setOnClickListener {
            FirebasePatches.statData += "6"

            FirebasePatches.stepSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.isNextXData}"
            FirebasePatches.nextField =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.nextFieldData}"
            FirebasePatches.nextBoard =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.prevStepData}"
            FirebasePatches.boardStateSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.data}"
            FirebasePatches.winSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.winPositionData}"
            FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.statData}"
            FirebasePatches.exitCode = "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.exitCodeData}"

            finish()
        }
        binding.button7.setOnClickListener {
            FirebasePatches.statData += "7"

            FirebasePatches.stepSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.isNextXData}"
            FirebasePatches.nextField =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.nextFieldData}"
            FirebasePatches.nextBoard =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.prevStepData}"
            FirebasePatches.boardStateSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.data}"
            FirebasePatches.winSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.winPositionData}"
            FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.statData}"
            FirebasePatches.exitCode = "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.exitCodeData}"

            finish()
        }
        binding.button8.setOnClickListener {
            FirebasePatches.statData += "8"

            FirebasePatches.stepSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.isNextXData}"
            FirebasePatches.nextField =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.nextFieldData}"
            FirebasePatches.nextBoard =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.prevStepData}"
            FirebasePatches.boardStateSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.data}"
            FirebasePatches.winSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.winPositionData}"
            FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.statData}"
            FirebasePatches.exitCode = "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.exitCodeData}"

            finish()
        }
        binding.button9.setOnClickListener {
            FirebasePatches.statData += "9"

            FirebasePatches.stepSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.isNextXData}"
            FirebasePatches.nextField =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.nextFieldData}"
            FirebasePatches.nextBoard =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.prevStepData}"
            FirebasePatches.boardStateSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.data}"
            FirebasePatches.winSuper =
                "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.winPositionData}"
            FirebasePatches.refSuper = "${FirebasePatches.servers}/${FirebasePatches.statData}"
            FirebasePatches.exitCode = "${FirebasePatches.servers}/${FirebasePatches.statData}/${FirebasePatches.exitCodeData}"

            finish()
        }
    }
}

