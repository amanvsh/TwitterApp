package com.example.twitterapp.util

import android.content.Context
import android.widget.Toast
import com.example.twitterapp.main.TwitterApplication

fun showToast(message: String){
    Toast.makeText(TwitterApplication.getInstance(), message, Toast.LENGTH_LONG).show()
}

fun isDetailsValid(email: String, password: String) : Boolean{
    return email.isNotEmpty() && password.isNotEmpty()
}
