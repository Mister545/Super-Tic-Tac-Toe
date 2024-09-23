package com.exemple.ticktacktoe.Servers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.exemple.ticktacktoe.Firebase.FirebasePatches
import com.exemple.ticktacktoe.Firebase.FirebaseService
import com.exemple.ticktacktoe.Game.ConstForActivity
import com.exemple.ticktacktoe.Game.MainActivity
import com.exemple.ticktacktoe.databinding.FragmentWaitingPlayersBinding

class FragmentWaitingPlayers : DialogFragment() {

    private val firebaseService = FirebaseService()
    private lateinit var binding: FragmentWaitingPlayersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWaitingPlayersBinding.inflate(layoutInflater, container, false)

        val typeGame = arguments?.getString(ConstForActivity.SIMPLE_OR_SUPER.toString())
        binding.button.setOnClickListener {
            if (typeGame == "simple") {
                firebaseService.getPlayersNumSimple(FirebasePatches.playersNum) {
                    firebaseService.setPlayersNum(it - 1, FirebasePatches.playersNum)
                }
                firebaseService.setExitCode(1, FirebasePatches.exitCodeSimple)
            }else if (typeGame == "super"){
                firebaseService.getPlayersNumSuper(FirebasePatches.playersNumSuper) {
                    firebaseService.setPlayersNumSuper(it - 1, FirebasePatches.playersNumSuper)
                }
                firebaseService.setExitCode(1, FirebasePatches.exitCodeSuper)
            }
            val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            dismiss()
        }

        return binding.root
    }
}