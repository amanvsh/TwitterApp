package com.example.twitterapp.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitterapp.model.LoginSignUpResponse
import com.example.twitterapp.model.ResponseType
import com.example.twitterapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private var repository: UserRepository) : ViewModel() {

    private val _response: MutableStateFlow<LoginSignUpResponse> = MutableStateFlow(LoginSignUpResponse(false, "", ResponseType.DEFAULT))
    val response: StateFlow<LoginSignUpResponse> = _response.asStateFlow()

    var isLoading = mutableStateOf(false)

     fun isUserLoggedIn(): String? {
        return repository.isUserLoggedIn()
    }

    fun signUpUser(email: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            _response.value  = repository.signUpUser(email, password)
            isLoading.value = false
        }
    }

    fun signInUser(email: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            _response.value = repository.signInUser(email, password)
            isLoading.value = false
        }
    }

    fun logOutUser(){
        repository.logOutUser()
    }

}