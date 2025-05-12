package com.example.myapplication

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase (if needed)
        FirebaseApp.initializeApp(this)
        // Any other initialization code you need
    }
}
