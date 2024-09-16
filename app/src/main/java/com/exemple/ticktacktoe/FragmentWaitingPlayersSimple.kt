package com.exemple.ticktacktoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.exemple.ticktacktoe.Game.FirebaseService
import com.exemple.ticktacktoe.databinding.FragmentWaitingPlayersBinding

class FragmentWaitingPlayersSimple : DialogFragment() {

    private val firebaseService = FirebaseService()
    private lateinit var binding: FragmentWaitingPlayersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaitingPlayersBinding.inflate(layoutInflater, container, false)

        binding.button.setOnClickListener {
            firebaseService.getPlayersNumSimple(FirebasePatches.playersNum) {
                firebaseService.setPlayersNum(it - 1, FirebasePatches.playersNum)
            }
            firebaseService.setExitCode(1, FirebasePatches.exitCodeSimple)
            dismiss()
        }
        firebaseService.getCodeRoom(FirebasePatches.rooms){ it, type->
            if (isAdded && isVisible) {
                requireActivity().runOnUiThread {
                    val codeRoom = "$type : $it"
                    binding.textView2.text = codeRoom
                }
            }
        }

        return binding.root
    }
}
