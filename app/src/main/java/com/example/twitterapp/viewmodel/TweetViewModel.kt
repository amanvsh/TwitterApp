package com.example.twitterapp.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitterapp.model.Tweet
import com.example.twitterapp.repository.TweetsRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject


@HiltViewModel
class TweetViewModel @Inject constructor(private var repository: TweetsRepository) : ViewModel() {

    private val _postTweet = MutableStateFlow("")
    val postTweet: StateFlow<String> = _postTweet

    private val _userTweetList = MutableStateFlow<List<Tweet>>(emptyList())
    val userTweetList: StateFlow<List<Tweet>> = _userTweetList

    var isLoading = mutableStateOf(false)

    fun postTweet(tweet: Tweet) {
        viewModelScope.launch {
            isLoading.value = true
            _postTweet.value = repository.postTweet(tweet)
            isLoading.value = false
        }
    }

    suspend fun getAllUserTweets() {
        viewModelScope.launch {
            isLoading.value = true
            _userTweetList.value = repository.getAllUserTweets()
            isLoading.value = false
        }
    }

    suspend fun compressBitmap(bitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        isLoading.value = true
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        val compressedData = outputStream.toByteArray()
        isLoading.value = false
        return@withContext BitmapFactory.decodeByteArray(compressedData, 0, compressedData.size)
    }


}