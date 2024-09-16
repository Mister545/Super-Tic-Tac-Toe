package com.exemple.desk.dialighelper.accounhelper

import android.widget.Toast
import com.exemple.ticktacktoe.Auth.GoogleAccConst
import com.exemple.ticktacktoe.FirebasePatches
import com.exemple.ticktacktoe.Game.FirebaseService
import com.exemple.ticktacktoe.MainActivity
import com.exemple.ticktacktoe.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class AccountHelper(act: MainActivity) {
    private val act = act
    private lateinit var signInClient: GoogleSignInClient
    val mAuth = FirebaseAuth.getInstance()
    private val firebaseService = FirebaseService()
    fun signUpWithEmail(email:String, password:String, name: String){
        if(email.isNotEmpty() && password.isNotEmpty()){
            act.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{task->

                if(task.isSuccessful){
                    firebaseService.setUserName(FirebasePatches.users,
                        mAuth.uid.toString(), name)
                    sendEmailVerification(task.result?.user!!)
                    act.uiUpdate(task.result?.user)
                }else{
                    Toast.makeText(act, act.resources.getString(R.string.sign_up_error), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun signInWithEmail(email:String, password:String, name: String){
        if(email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()){
            act.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{task->

                if(task.isSuccessful){

                    firebaseService.setUserName(FirebasePatches.users,
                        mAuth.uid.toString(), name)
                    act.uiUpdate(task.result?.user)
                }else{
                    Toast.makeText(act, act.resources.getString(R.string.sign_in_error), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(act.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(act, gso)
    }

    fun signInWithGoogle(){
        signInClient = getSignInClient()
        val intent = signInClient.signInIntent
        act.startActivityForResult(intent, GoogleAccConst.GOOGLE_SIGN_IN_REQUEST_CODE)
    }
    fun signInFirebaseWithGoogle(token:String){
        val credential = GoogleAuthProvider.getCredential(token, null)
        act.mAuth.signInWithCredential(credential).addOnCompleteListener {task ->
            if(task.isSuccessful){
                Toast.makeText(act, "Sign In done", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sendEmailVerification(user: FirebaseUser){
        user.sendEmailVerification().addOnCompleteListener{task->
            if(task.isSuccessful){
                Toast.makeText(act, act.resources.getString(R.string.send_verification_email_done), Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(act, act.resources.getString(R.string.send_verification_email_error), Toast.LENGTH_LONG).show()

            }
        }
    }
}
