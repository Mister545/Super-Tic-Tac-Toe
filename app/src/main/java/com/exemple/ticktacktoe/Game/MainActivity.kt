// MainActivity.kt
package com.exemple.ticktacktoe.Game

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.exemple.desk.dialighelper.accounhelper.AccountHelper
import com.exemple.ticktacktoe.DialogHelper.DialogHelper
import com.exemple.ticktacktoe.Auth.GoogleAccConst
import com.exemple.ticktacktoe.Firebase.FirebasePatches
import com.exemple.ticktacktoe.Firebase.FirebaseService
import com.exemple.ticktacktoe.Game.Offline.OfflineSimple
import com.exemple.ticktacktoe.Game.Offline.OfflineSuper
import com.exemple.ticktacktoe.R
import com.exemple.ticktacktoe.Game.Online.SimpleTicTacToe
import com.exemple.ticktacktoe.Game.Online.SuperTicTacToe
import com.exemple.ticktacktoe.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val mAuth = FirebaseAuth.getInstance()
    private val dialogHelper = DialogHelper()
    private val firebaseService = FirebaseService()
    private var online = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val a = fun(){
            Log.d("ooo", "dkjvuhdvgyvgydfvdybcudubc")
        }

        a()

//        firebaseService.getAllBase {
//
//        }
        firebaseService.getListServers("Simple"){

        }
        firebaseService.getListServers("Super"){

        }
        firebaseService.getListServers("RoomSimple"){

        }
        firebaseService.getListServers("RoomSuper"){

        }

//        binding.imageButtonAccount.setOnClickListener {
//            if (mAuth.currentUser != null){
//                val intent = Intent(this, SuperTicTacToe::class.java)
//                startActivity(intent)
//            } else
//            dialogHelper.createSignDialog(DialogConst.SIGN_UP_STATE)
//        }

        binding.bRooms.setOnClickListener {
            dialogHelper.createRoomDialog(this)
        }

        binding.chip.setOnCheckedChangeListener { _, isChecked ->
            online = isChecked
        }


        binding.bPlaySimple.setOnClickListener {
                if (online){
                    val intent = Intent(this, SimpleTicTacToe::class.java)
                    startActivity(intent)
                }else{
                    val intent = Intent(this, OfflineSimple::class.java)
                    startActivity(intent)
                }
        }
        binding.bPlaySuper.setOnClickListener {
            if (online){
                val intent = Intent(this, SuperTicTacToe::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, OfflineSuper::class.java)
                startActivity(intent)
            }
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                val account = task.getResult(ApiException::class.java)
                if (account != null)
                    AccountHelper(this).signInFirebaseWithGoogle(account.idToken!!)
            }catch (e: ApiException){
                Log.d("MyLog", "Api Exception ${e.message}")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }

    fun uiUpdate(user: FirebaseUser?){
        if(user == null){
            binding.tvNameUser.text = resources.getString(R.string.not_reg)

        } else {

            firebaseService.getPlayerName(FirebasePatches.users+"/"+mAuth.uid+"/"+"name"){
                Log.d("ooo", "playerName ${mAuth.uid}")

                binding.tvNameUser.text = it
            }
        }

    }

    private fun replaceFragment(fragment: DialogFragment) {
        if (!isFinishing && !supportFragmentManager.isDestroyed) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()

            if (!supportFragmentManager.isStateSaved) {
                fragmentTransaction.replace(binding.fragmentConteinerSuper.id, fragment)
                fragmentTransaction.commit()
            } else {
                fragmentTransaction.replace(binding.fragmentConteinerSuper.id, fragment)
                fragmentTransaction.commitAllowingStateLoss()
            }
        } else {
            println("Замінити фрагмент неможливо: активність знищена або завершується")
        }
    }

    private fun removeFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentConteinerSuper)
        if (fragment != null && !supportFragmentManager.isStateSaved) {
            supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commit()
        }
    }}
