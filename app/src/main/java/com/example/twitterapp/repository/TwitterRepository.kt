package com.example.twitterapp.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TwitterRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun signUpUser(email: String, password: String): String{
        Log.v("FireBaseTest","signUpUser called")
        return try {
            val result = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            Log.v("FireBaseTest",result.toString())
            "SignUp successfully"
        } catch (e: Exception) {
            Log.v("FireBaseTest",e.toString())
            e.localizedMessage ?: "SignUp Failed"
        }

    }

    suspend fun signInUser(email: String, password: String): String {
        Log.v("FireBaseTest","signInUser called")
        return try {
            val result =  FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            val user = result.user
            "SignIn successfully"
        } catch (e: Exception) {
            e.localizedMessage ?: "SignIn Failed"
        }
    }

}