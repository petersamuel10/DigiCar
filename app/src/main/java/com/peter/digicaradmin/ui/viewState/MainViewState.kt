package com.peter.digicaradmin.ui.viewState

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.peter.digicaradmin.data.model.ConsultationModel
import com.peter.digicaradmin.data.model.Temp

sealed class MainViewState {

    object Idle : MainViewState()
    object Loading : MainViewState()

    data class Login(val user: Task<AuthResult>?) : MainViewState()
    data class Temp(val temp: com.peter.digicaradmin.data.model.Temp) : MainViewState()
    data class Consultation(val data: ArrayList<ConsultationModel>) : MainViewState()
    data class CreateAccount(val user: Task<Void>?) : MainViewState()
    data class Error(val error: String?) : MainViewState()
}