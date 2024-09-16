package com.exemple.ticktacktoe.DialogHelper

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.exemple.desk.dialighelper.accounhelper.AccountHelper
import com.exemple.ticktacktoe.MainActivity
import com.exemple.ticktacktoe.R
import com.exemple.ticktacktoe.Servers
import com.exemple.ticktacktoe.SimpleTicTacToe
import com.exemple.ticktacktoe.SuperTicTacToe
import com.exemple.ticktacktoe.databinding.RoomDialogBinding
import com.exemple.ticktacktoe.databinding.SignDialogBinding
import kotlin.random.Random

class DialogHelper(private val act: MainActivity){
    val accHelper = AccountHelper(act)
    val servers = Servers()
    fun createRoomDialog(){
        val builder = AlertDialog.Builder(act)
        val binding = RoomDialogBinding.inflate(act.layoutInflater)
        val view = binding.root
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        binding.bCreateRoomSimple.setOnClickListener {
            servers.createRoom((100000..10000000).random())
            val intent = Intent(act, SimpleTicTacToe::class.java)
            act.startActivity(intent)
        }
        binding.bCreateRoomSuper.setOnClickListener {
            servers.createRoom((100000..10000000).random())
            val intent = Intent(act, SuperTicTacToe::class.java)
            act.startActivity(intent)
        }
        binding.bSignInRoom.setOnClickListener {

            val code = binding.eCodeRoom.text.toString()
            if (code != "") {
                servers.signInRoom(code.toInt()) { it, type ->
                    Log.d("ooo", "type -------- $type")
                    if (it == false) {
                        binding.textView3.text = "wrong code"
                        binding.textView3.visibility = View.VISIBLE
                    } else if (type == "ServerSimple") {
                        val intent = Intent(act, SimpleTicTacToe::class.java)
                        act.startActivity(intent)
                        binding.textView3.text = "Nice"
                        binding.textView3.visibility = View.VISIBLE
                    } else {
                        val intent = Intent(act, SuperTicTacToe::class.java)
                        act.startActivity(intent)
                        binding.textView3.text = "Nice"
                        binding.textView3.visibility = View.VISIBLE
                    }
                }
            }else{
                binding.textView3.text = "wrong code"
                binding.textView3.visibility = View.VISIBLE
            }

        }
    }
    fun createSignDialog(index:Int){
        val builder = AlertDialog.Builder(act)
        val binding = SignDialogBinding.inflate(act.layoutInflater)
        val view = binding.root
        builder.setView(view)

        setDialogState(index, binding)

        val dialog = builder.create()
        binding.bSignUpIn.setOnClickListener{
            setOnClickSignUpIn(index, binding, dialog)
        }
        dialog.show()
        binding.bForgetP.setOnClickListener{
            setOnClickResetPassword(binding, dialog)
        }
        binding.btGoogleSignIn.setOnClickListener{
            accHelper.signInWithGoogle()
        }
        binding.tvHaveAnAcc.setOnClickListener {
            dialog.dismiss()
            createSignDialog(DialogConst.SIGN_IN_STATE)
            binding.tvHaveAnAcc.visibility = View.GONE
        }

    }

    private fun setOnClickResetPassword(binding: SignDialogBinding, dialog: AlertDialog?) {
        if(binding.edSIgnInEmail.text.isNotEmpty()){
            act.mAuth.sendPasswordResetEmail(binding.edSIgnInEmail.text.toString()).addOnCompleteListener{task->

                    if(task.isSuccessful){
                        Toast.makeText(act, R.string.email_reset_wes_sent, Toast.LENGTH_LONG).show()
                    }
                }
            dialog?.dismiss()
        }
        else{
            binding.tvDialogMassage.visibility = View.VISIBLE
        }
    }

    private fun setOnClickSignUpIn(index: Int, binding: SignDialogBinding, dialog: AlertDialog) {

        dialog.dismiss()

        if(index == DialogConst.SIGN_UP_STATE){
            accHelper.signUpWithEmail(binding.edSIgnInEmail.text.toString(),
                binding.edSIgnPassword.text.toString(),
                binding.edtNick.text.toString())
        } else{
            accHelper.signInWithEmail(binding.edSIgnInEmail.text.toString(),
                binding.edSIgnPassword.text.toString(),
                binding.edtNick.text.toString())
        }
    }

    private fun setDialogState(index: Int, binding: SignDialogBinding) {
        if(index == DialogConst.SIGN_UP_STATE){
            binding.tvSignTitle.text = act.getString(R.string.ac_sign_up)
            binding.bSignUpIn.text = act.getString(R.string.sign_up_action)
        } else{
            binding.tvHaveAnAcc.visibility = View.INVISIBLE
            binding.tvSignTitle.text = act.getString(R.string.ac_sign_in)
            binding.bSignUpIn.text = act.getString(R.string.sign_in_action)
            binding.bForgetP.visibility = View.VISIBLE
        }
    }
}