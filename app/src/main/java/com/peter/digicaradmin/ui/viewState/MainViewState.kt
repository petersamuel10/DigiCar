package com.peter.digicaradmin.ui.viewState

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference

sealed class MainViewState {

    object Idle : MainViewState()
    object Loading : MainViewState()

    data class Login(val user: Task<AuthResult>?) : MainViewState()
    data class CreateAccount(val user: Task<Void>?) : MainViewState()
    data class Error(val error: String?) : MainViewState()
}