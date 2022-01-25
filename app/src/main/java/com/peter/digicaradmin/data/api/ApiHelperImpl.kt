package com.peter.digicaradmin.data.api

import com.church.ministry.util.NetworkHelper
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.peter.digicaradmin.data.model.Temp
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val apiService: ApiService,
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
) : ApiHelper {

    override suspend fun login(email: String, password: String): Task<AuthResult>? {
        return if (networkHelper.isNetworkConnected())
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {}
        else
            null
    }

    override suspend fun temp(): Temp {
        return apiService.temp()
    }

    override suspend fun createNewAccount(
        userName: String,
        password: String,
        phoneNum: String
    ): Task<Void>? {

        return if (networkHelper.isNetworkConnected()) {

            val user = hashMapOf("userName" to userName, "phoneNumber" to phoneNum, "password" to password)
            db.collection("users").document(userName)
                .set(user)
                .addOnCompleteListener {}
        } else
            null
    }

    override suspend fun logout() {
        firebaseAuth.currentUser
    }

}