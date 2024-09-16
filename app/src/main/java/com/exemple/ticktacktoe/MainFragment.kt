//package com.exemple.ticktacktoe
//
//
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.Fragment
//import com.exemple.ticktacktoe.Auth.FragmentAuth
//import com.exemple.ticktacktoe.databinding.ActivityMainBinding
//import com.exemple.ticktacktoe.databinding.FargmentMainBinding
//import com.exemple.ticktacktoe.databinding.FragmentAuthBinding
//import com.exemple.ticktacktoe.databinding.FragmentWaitingPlayersBinding
//
//class MainFragment  : Fragment() {
//
//    private lateinit var binding: FargmentMainBinding
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//
//            binding = FargmentMainBinding.inflate(layoutInflater, container, false)
//
//            binding.imageButtonAccount.setOnClickListener {
//                replaceFragment(FragmentAuth())
//            }
//
//            binding.bPlaySimple.setOnClickListener {
//                val intent = Intent(this, SimpleTicTacToe::class.java)
//                startActivity(intent)
//
//            }
//            binding.bPlaySuper.setOnClickListener {
//                val intent = Intent(this, SuperTicTacToe::class.java)
//                startActivity(intent)
//            }
//
//        return binding.root
//    }
//    }
//
//    private fun replaceFragment(fragment: DialogFragment) {
//        if (!isFinishing && !supportFragmentManager.isDestroyed) {
//            val fragmentTransaction = supportFragmentManager.beginTransaction()
//
//            if (!supportFragmentManager.isStateSaved) {
//                fragmentTransaction.replace(binding.fragmentConteinerSuper.id, fragment)
//                fragmentTransaction.commit()
//            } else {
//                fragmentTransaction.replace(binding.fragmentConteinerSuper.id, fragment)
//                fragmentTransaction.commitAllowingStateLoss()
//            }
//        } else {
//            println("Замінити фрагмент неможливо: активність знищена або завершується")
//        }
//    }
//
//    private fun removeFragment() {
//        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentConteinerSuper)
//        if (fragment != null && !supportFragmentManager.isStateSaved) {
//            supportFragmentManager.beginTransaction()
//                .remove(fragment)
//                .commit()
//        }
//    }}
//
