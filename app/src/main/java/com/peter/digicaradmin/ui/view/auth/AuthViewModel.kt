package com.peter.digicaradmin.ui.view.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class AuthViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: StateFlow<MainViewState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.Login -> login(it.email, it.password)
                }
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = MainViewState.Loading
            val result = repository.login(email, password)
            if (result != null)
                _state.value = if (result.isSuccessful)
                    MainViewState.Login(result) else MainViewState.Error("sign In failure")
        }
    }
}