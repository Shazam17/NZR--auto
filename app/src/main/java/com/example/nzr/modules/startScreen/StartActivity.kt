package com.example.nzr.modules.startScreen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.acitivity_start.*
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.GoogleAuthProvider


class StartActivity : AppCompatActivity() {


    var RC_SIGN_IN = 9001
    var TAG = "startActivity"
    var auth = FirebaseAuth.getInstance()

    lateinit var mGoogleSignInClient :GoogleSignInClient

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    Log.d(TAG,user?.displayName.toString())

                } else {
                    // If sign in fails, display a message to the user.


                }


            }
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account!!)

        } catch (e: ApiException) {
            Log.w("log", "signInResult:failed code=" + e.statusCode)

        }

    }
    fun signIn(){
        var signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,RC_SIGN_IN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.nzr.R.layout.acitivity_start)

        var auth : FirebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(com.example.nzr.R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener {
            signIn()
        }
    }
}


//        signUp.setOnClickListener {
//            auth
//                .createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
//                .addOnCompleteListener{
//                    if(it.isSuccessful){
//                        val user  = auth.currentUser
//                    }else{
//                        Log.e("main","errorSignUp")
//                    }
//                }
//        }
//
//        signIn.setOnClickListener{
//            auth
//                .signInWithEmailAndPassword(email.text.toString(),password.text.toString())
//                .addOnCompleteListener{
//                    if(it.isSuccessful){
//                        val user  = auth.currentUser
//                    }else{
//                        Log.e("main","errorSignIn")
//
//                    }
//                }
//        }
//        exitBtn.setOnClickListener{
//            auth.signOut()
//
//        }