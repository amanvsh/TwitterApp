package com.example.twitterapp.model

data class LoginSignUpResponse(
    val status: Boolean,
    val message: String,
    val responseType: ResponseType
)

enum class ResponseType {
    DEFAULT,
    SIGN_IN,
    SIGN_UP
}
