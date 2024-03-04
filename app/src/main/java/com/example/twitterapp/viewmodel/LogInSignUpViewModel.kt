package com.example.twitterapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitterapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInSignUpViewModel @Inject constructor(private var repository: UserRepository)   : ViewModel() {

    private val _response = MutableStateFlow("")
    val response: StateFlow<String> = _response

    fun signUpUser(email: String, password: String) {
        viewModelScope.launch {
            Log.v("FireBaseTest","$email -signUp- $password")
            _response.value = repository.signUpUser(email, password)
        }
    }

    fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            Log.v("FireBaseTest","$email -signIn- $password")
            _response.value = repository.signInUser(email, password)
        }
    }


}