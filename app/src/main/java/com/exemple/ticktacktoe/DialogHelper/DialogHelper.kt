package com.exemple.ticktacktoe.DialogHelper

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.exemple.desk.dialighelper.accounhelper.AccountHelper
import com.exemple.ticktacktoe.Game.ConstForActivity
import com.exemple.ticktacktoe.Game.MainActivity
import com.exemple.ticktacktoe.R
import com.exemple.ticktacktoe.Servers.Servers
import com.exemple.ticktacktoe.Game.Online.SimpleTicTacToe
import com.exemple.ticktacktoe.Game.Online.SuperTicTacToe
import com.exemple.ticktacktoe.databinding.RoomDialogBinding
import com.exemple.ticktacktoe.databinding.SignDialogBinding
import com.exemple.ticktacktoe.databinding.WinDialogBinding

class DialogHelper {
    val servers = Servers()


    fun createWinDialogSuper(winner: String, act: Activity) {
        act.let { context ->
            val builder = AlertDialog.Builder(context)
            val binding = WinDialogBinding.inflate(context.layoutInflater)
            val view = binding.root
            builder.setView(view)
            val dialog = builder.create()

            binding.tWinner.text = "Winner: $winner"
            binding.button10.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            }
            dialog.show()
        } ?: Log.e("DialogHelper", "Context is null, cannot create dialog")
    }

    fun createWinDialogSimple(winner: String, act: SimpleTicTacToe) {
        val builder = AlertDialog.Builder(act)
        val binding = WinDialogBinding.inflate(act.layoutInflater)
        val view = binding.root
        builder.setView(view)
        builder.create()
        val dialog = builder.create()
        binding.tWinner.text = winner
        binding.button10.setOnClickListener {
            val intent = Intent(act, MainActivity::class.java)
            act.startActivity(intent)
        }
        dialog.show()
    }

    fun createRoomDialog(act: MainActivity) {
        val builder = AlertDialog.Builder(act)
        val binding = RoomDialogBinding.inflate(act.layoutInflater)
        val view = binding.root
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        binding.bCreateRoomSimple.setOnClickListener {
            val intent = Intent(act, SimpleTicTacToe::class.java)
            intent.putExtra(ConstForActivity.ROOM_CODE.toString(), "true")

            servers.createRoom("simple")
            act.startActivity(intent)
        }
        binding.bCreateRoomSuper.setOnClickListener {
            val intent = Intent(act, SuperTicTacToe::class.java)
            intent.putExtra(ConstForActivity.ROOM_CODE.toString(), "true")

            servers.createRoom("superr")
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
            } else {
                binding.textView3.text = "wrong code"
                binding.textView3.visibility = View.VISIBLE
            }

        }
    }

    fun createSignDialog(index: Int, act: MainActivity) {
        val accHelper = AccountHelper(act)

        val builder = AlertDialog.Builder(act)
        val binding = SignDialogBinding.inflate(act.layoutInflater)
        val view = binding.root
        builder.setView(view)

        setDialogState(index, binding, act)

        val dialog = builder.create()
        binding.bSignUpIn.setOnClickListener {
            setOnClickSignUpIn(index, binding, dialog, accHelper)
        }
        dialog.show()
        binding.bForgetP.setOnClickListener {
            setOnClickResetPassword(binding, dialog, act)
        }
        binding.btGoogleSignIn.setOnClickListener {
            accHelper.signInWithGoogle()
        }
        binding.tvHaveAnAcc.setOnClickListener {
            dialog.dismiss()
            createSignDialog(DialogConst.SIGN_IN_STATE, act)
            binding.tvHaveAnAcc.visibility = View.GONE
        }

    }

    private fun setOnClickResetPassword(
        binding: SignDialogBinding,
        dialog: AlertDialog?,
        act: MainActivity
    ) {
        if (binding.edSIgnInEmail.text.isNotEmpty()) {
            act.mAuth.sendPasswordResetEmail(binding.edSIgnInEmail.text.toString())
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        Toast.makeText(act, R.string.email_reset_wes_sent, Toast.LENGTH_LONG).show()
                    }
                }
            dialog?.dismiss()
        } else {
            binding.tvDialogMassage.visibility = View.VISIBLE
        }
    }

    private fun setOnClickSignUpIn(
        index: Int,
        binding: SignDialogBinding,
        dialog: AlertDialog,
        accHelper: AccountHelper
    ) {

        dialog.dismiss()

        if (index == DialogConst.SIGN_UP_STATE) {
            accHelper.signUpWithEmail(
                binding.edSIgnInEmail.text.toString(),
                binding.edSIgnPassword.text.toString(),
                binding.edtNick.text.toString()
            )
        } else {
            accHelper.signInWithEmail(
                binding.edSIgnInEmail.text.toString(),
                binding.edSIgnPassword.text.toString(),
                binding.edtNick.text.toString()
            )
        }
    }

    private fun setDialogState(index: Int, binding: SignDialogBinding, act: MainActivity) {
        if (index == DialogConst.SIGN_UP_STATE) {
            binding.tvSignTitle.text = act.getString(R.string.ac_sign_up)
            binding.bSignUpIn.text = act.getString(R.string.sign_up_action)
        } else {
            binding.tvHaveAnAcc.visibility = View.INVISIBLE
            binding.tvSignTitle.text = act.getString(R.string.ac_sign_in)
            binding.bSignUpIn.text = act.getString(R.string.sign_in_action)
        }
    }
}
   