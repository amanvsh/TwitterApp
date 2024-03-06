package com.example.twitterapp.repository

import android.util.Log
import com.example.twitterapp.model.LoginSignUpResponse
import com.example.twitterapp.model.ResponseType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor() {

     fun isUserLoggedIn(): String?{
       val status: FirebaseUser? =  FirebaseAuth.getInstance().currentUser
        return if (status != null) {
            return status.email
        } else {
            null
        }
    }

    suspend fun signUpUser(email: String, password: String): LoginSignUpResponse{
        return try {
            val result = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            val user = result.user?.email
            LoginSignUpResponse(status = true, message = "$user SignUp successfully", responseType = ResponseType.SIGN_UP)
        } catch (e: Exception) {
            LoginSignUpResponse(status = false, message = "$email : ${e.localizedMessage}", responseType = ResponseType.SIGN_UP)
        }

    }

    suspend fun signInUser(email: String, password: String): LoginSignUpResponse {
        return try {
            val result =  FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            val user = result.user?.email
            LoginSignUpResponse(status = true, "$user Login Successful", responseType = ResponseType.SIGN_IN)
        } catch (e: Exception) {
            LoginSignUpResponse(status = false, "$email : ${e.localizedMessage}", responseType = ResponseType.SIGN_IN)
        }
    }

    fun logOutUser(){
        FirebaseAuth.getInstance().signOut()
    }

}