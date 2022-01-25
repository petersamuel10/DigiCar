package com.peter.digicaradmin.ui.intent

sealed
class MainIntent {

    class Login(val email: String, val password: String) : MainIntent()
    object Temp : MainIntent()
    class CreateAccount(val userName: String, val password: String, val phoneNum: String) :
        MainIntent()
}