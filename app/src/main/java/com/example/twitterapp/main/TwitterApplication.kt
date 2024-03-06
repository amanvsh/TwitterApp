package com.example.twitterapp.main

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TwitterApplication : Application() {
    companion object {
        private lateinit var instance: TwitterApplication

        fun getInstance(): TwitterApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this // Assign the application instance
    }
}