package com.peter.digicaradmin.data.api

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference


interface ApiHelper {

    suspend fun login(email: String, password: String): Task<AuthResult>?
    suspend fun createNewAccount(userName: String, password: String, phoneNum:String): Task<Void>?
    suspend fun logout()
}