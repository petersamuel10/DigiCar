package com.peter.digicaradmin.ui.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peter.digicaradmin.R
import com.peter.digicaradmin.ui.view.MainActivity
import com.peter.digicaradmin.ui.view.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        ic_splash.animate()
            .setDuration(7000)
            .withEndAction {
                finishAct(Firebase.auth.currentUser != null)
            }
            .start()
    }


    private fun finishAct(logged: Boolean) {
        startActivity(
            Intent(
                this,
                if (logged) MainActivity::class.java else AuthActivity::class.java
            )
        )
        finish()
    }
}