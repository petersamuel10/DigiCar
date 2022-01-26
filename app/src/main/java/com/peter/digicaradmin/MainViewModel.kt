package com.peter.digicaradmin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firestore.v1.Document
import com.peter.digicaradmin.data.repository.MainRepository
import com.peter.digicaradmin.ui.intent.MainIntent
import com.peter.digicaradmin.ui.viewState.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val db:FirebaseFirestore
) : ViewModel() {

    val mainIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: StateFlow<MainViewState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            mainIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.Temp -> getTemp()
                    is MainIntent.Consultation -> getConsultation()
                    is MainIntent.CreateAccount -> createNewAccount(
                        it.userName,
                        it.password,
                        it.phoneNum
                    )
                }
            }
        }
    }

    private fun getTemp() {
        viewModelScope.launch {
            _state.value = MainViewState.Loading
            _state.value = try {
                MainViewState.Temp(repository.temp())
            } catch (e: Exception) {
                MainViewState.Error(e.localizedMessage)
            }
        }
    }

    private fun createNewAccount(userName: String, password: String, phoneNum: String) {
        viewModelScope.launch {
            _state.value = MainViewState.Loading
            val result = repository.createNewAccount(userName, password, phoneNum)
            if (result != null)
                _state.value = MainViewState.CreateAccount(result)
        }
    }

    private fun getConsultation() {
        val data:ArrayList<ArrayList<String>> = arrayListOf()
        viewModelScope.launch {
            _state.value = MainViewState.Loading
             db.collection("consultation")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            data.add( arrayListOf(document["consultation"].toString()))
                            _state.value = MainViewState.Consultation(data)
                        }
                    }
                    .addOnFailureListener { exception ->

                    }
        }
    }

}
