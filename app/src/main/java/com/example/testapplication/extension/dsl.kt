package com.example.testapplication.extension

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.testapplication.BuildConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun postDelayed(delayInMillis: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(Runnable(block), delayInMillis)
}

fun LifecycleOwner.postDelayed(delayInMillis: Long, block: () -> Unit) {
    lifecycleScope.launch {
        delay(delayInMillis)
        block()
    }
}

inline fun debugMode(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}