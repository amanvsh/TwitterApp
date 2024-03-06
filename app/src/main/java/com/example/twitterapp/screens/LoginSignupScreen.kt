package com.example.twitterapp.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.twitterapp.model.LoginSignUpResponse
import com.example.twitterapp.model.ResponseType
import com.example.twitterapp.util.isDetailsValid
import com.example.twitterapp.util.showToast
import com.example.twitterapp.viewmodel.UserViewModel

@Composable
fun LoginSignupScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (itemId: String) -> Unit
) {

    val focusManager = LocalFocusManager.current
    val viewModel: UserViewModel = hiltViewModel()

    val loading = viewModel.isLoading.value
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    LaunchedEffect(key1 = viewModel.response) {
        viewModel.response.collect { response ->
            handleResponse(response, email, onNavigationRequested)
        }
    }

    Box {
        Surface(modifier = modifier.fillMaxSize()) {
            ColumnLayout(
                email,
                password,
                onEmailChange = { newEmail -> email = newEmail },
                onPasswordChange = { newPassword -> password = newPassword }
            ) {
                SignInSignUpButtons(
                    email,
                    password,
                    viewModel,
                    ::onSignInClicked,
                    ::onSignUpClicked,
                    focusManager
                )
                if (loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun ColumnLayout(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {onEmailChange(it)},
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp, 20.dp, 0.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {onPasswordChange(it)},
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 10.dp, 20.dp, 10.dp)
        )
        content()
    }
}

@Composable
private fun SignInSignUpButtons(
    email: String,
    password: String,
    viewModel: UserViewModel,
    onSignInClicked: (email: String, passWord: String, viewModel: UserViewModel) -> Unit,
    onSignUpClicked: (email: String, passWord: String, viewModel: UserViewModel) -> Unit,
    focusManager: FocusManager
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp, 10.dp, 25.dp, 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = {
            focusManager.clearFocus()
            onSignInClicked(email, password, viewModel)
        }) {
            Text("Sign In", fontSize = 18.sp)
        }
        Button(onClick = {
            focusManager.clearFocus()
            onSignUpClicked(email, password, viewModel)
        }) {
            Text("Sign Up", fontSize = 18.sp)
        }
    }
}

private fun handleResponse(response: LoginSignUpResponse, email: String, onNavigationRequested: (String) -> Unit) {
    Log.v("LoginSignUp", "response.collect -> $response")
    if (response.status && response.responseType == ResponseType.SIGN_IN) {
        onNavigationRequested(email)
    }
    if (response.message.isNotEmpty()) {
        showToast(response.message)
    }
}

private fun onSignInClicked(email: String, password: String, viewModel: UserViewModel) {
    if (isDetailsValid(email = email, password = password)) {
        viewModel.signInUser(email = email, password = password)
    } else {
        showToast("Email or Password can not be empty")
    }
}

private fun onSignUpClicked(email: String, password: String, viewModel: UserViewModel) {
    if (isDetailsValid(email = email, password = password)) {
        viewModel.signUpUser(email = email, password = password)
    } else {
        showToast("Email or Password can not be empty")
    }
}


