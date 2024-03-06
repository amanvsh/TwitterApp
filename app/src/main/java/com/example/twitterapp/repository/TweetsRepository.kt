package com.example.twitterapp.repository

import android.net.Uri
import android.util.Log
import com.example.twitterapp.model.Tweet
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class TweetsRepository @Inject constructor() {

    suspend fun postTweet(tweet: Tweet): String {
        Log.v("FireBaseTest", "postTweet called $tweet")
        return try {
            if (tweet.imgUploadUri != null) {
                uploadTweetWithImage(tweet)
            } else{
                FirebaseFirestore.getInstance().collection("userTweets").document().set(tweet)
                    .await()
        }
            Log.v("FireBaseTest", "Post success -->>>>")
            "Post success"
        } catch (e: Exception) {
            Log.v("FireBaseTest", e.toString())
            e.localizedMessage ?: "Post Failed"
        }
    }

    suspend fun getAllUserTweets(): List<Tweet> {
        Log.v("FireBaseTest", "signInUser called")
        return try {

            val collectionRef = FirebaseFirestore.getInstance().collection("userTweets")
            val querySnapshot = collectionRef.get().await()
            val documents = querySnapshot.documents
            val tweets = mutableListOf<Tweet>()
            for (document in documents) {
                val emailId = document.getString("email") ?: "" // Handle missing fields
                val text = document.getString("postText") ?: ""
                val imageUrl = document.getString("imgDownloadUrl") ?: ""
                val tweet = Tweet(email = emailId, postText = text, imgDownloadUrl = imageUrl)
                tweets.add(tweet)
            }
            tweets
        } catch (e: Exception) {
            e.localizedMessage ?: "Login Failed"
            emptyList()
        }
    }

    private suspend fun uploadImage(imgUploadUri: Uri, callback: (imageURI: Uri?) -> Unit) {
        try {
            val storageReference =
                FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}")
            storageReference.putFile(imgUploadUri).addOnCompleteListener { taskSnapshot ->
                if (taskSnapshot.isSuccessful) {
                    storageReference.downloadUrl.addOnSuccessListener { imageURI ->
                        callback(imageURI)  // Call back with the image URI
                    }
                } else {
                    callback(null) // Handle failure by calling back with null
                }

            }
        } catch (e: Exception) {
            Log.e("Image Upload", "UploadImage image failed : ${e.message}")
            callback(null) // Also call back with null in case of exceptions
        }
    }


    private suspend fun uploadTweetWithImage(tweet: Tweet): String? {
        var imageUrl: String? = null

            val storageReference = FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}")
            val uploadTask = tweet.imgUploadUri?.let { storageReference.putFile(it) }

            uploadTask?.await()

            if (uploadTask?.isSuccessful == true) {
                // Get the image URL after successful upload
                imageUrl = storageReference.downloadUrl.await().toString()

                val documentReference = FirebaseFirestore.getInstance().collection("userTweets").document()
                tweet.imgDownloadUrl= imageUrl // Add image URL to the tweet data
                documentReference.set(tweet).await()

                return documentReference.id
            } else {
                Log.e("Image Upload", "UploadImage image failed : ${uploadTask?.exception?.message}")
            }

        return null // Return null in case of any errors
    }

}