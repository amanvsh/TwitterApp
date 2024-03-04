package com.example.twitterapp.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor() {

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
            "Login successfully"
        } catch (e: Exception) {
            e.localizedMessage ?: "Login Failed"
        }
    }

}