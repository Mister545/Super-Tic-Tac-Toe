package com.exemple.ticktacktoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.exemple.ticktacktoe.databinding.FragmentSuperTicTacToeBinding
import com.exemple.ticktacktoe.databinding.WinFragmentBinding

class super_ticTacToe : DialogFragment() {

    lateinit var binding : FragmentSuperTicTacToeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuperTicTacToeBinding.inflate(inflater, container, false)
        return binding.root
    }
}