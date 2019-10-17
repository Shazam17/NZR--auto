package com.example.nzr.modules.startScreen

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.acitivity_start.*
import android.content.Intent
import android.provider.Settings
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.utils.VKUtils
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class StartActivity : AppCompatActivity() ,StartContract.StartView{
    lateinit var presenter : StartPresenter
    lateinit var codeWeb :String

    override fun getCode(): String? {
        return codeWeb
    }

    override fun getActivity():StartActivity{
        return this
    }

    override fun toNextScreen(){
        var intent = Intent(this,ChooseDepActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == presenter.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            presenter.handleSignInResult(task)
        }
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
            }
        }
        if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.nzr.R.layout.acitivity_start)
        codeWeb  = getString(com.example.nzr.R.string.default_web_client_id)
        presenter = StartPresenter(this)
        signInButton.setOnClickListener {
            presenter.signIn()
        }
        val fingerprints = VKUtils.getCertificateFingerprint(this, this.packageName)
        signVK.setOnClickListener{
            VK.login(this, arrayListOf(VKScope.WALL, VKScope.PHOTOS))
        }
    }
}
