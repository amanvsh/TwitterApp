package com.example.twitterapp.model

import android.net.Uri

data class Tweet(
    val email: String = "",
    val postText: String = "",
    val imgUploadUri: Uri? = null,
    var imgDownloadUrl: String = ""
)
