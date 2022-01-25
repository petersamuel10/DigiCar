package com.peter.digicaradmin.ui.view.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.peter.digicaradmin.R
import com.peter.digicaradmin.ui.intent.MainIntent
import com.peter.digicaradmin.ui.view.MainActivity
import com.peter.digicaradmin.ui.viewState.MainViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    public override fun onStart() {
        super.onStart()

        loginBtn.setOnClickListener {
            lifecycleScope.launch {
                authViewModel.userIntent.send(
                    MainIntent.Login(
                        nameInEd.text.toString(),
                        passwordInEd.text.toString()
                    )
                )
            }
        }
        observeViewModel()
    }

    private fun observeViewModel() {

        lifecycleScope.launch {
            authViewModel.state.collect {
                when (it) {
                    is MainViewState.Idle -> {

                    }
                    is MainViewState.Loading -> {
                        //progressDialog.show()
                    }

                    is MainViewState.Login -> {
                        // progressDialog.dismiss()
                        finishAct()
                    }
                    is MainViewState.Error -> {
                        // progressDialog.dismiss()
                        Toast.makeText(this@AuthActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun finishAct() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    // region variable
    private val authViewModel: AuthViewModel by viewModels()
    //endregion
    
}