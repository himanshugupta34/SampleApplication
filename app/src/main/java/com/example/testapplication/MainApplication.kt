package com.example.testapplication

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun getApplicationContext(): Context {
        return super.getApplicationContext()
    }
}