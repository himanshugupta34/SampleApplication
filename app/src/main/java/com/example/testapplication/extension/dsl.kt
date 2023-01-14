package com.example.testapplication.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.testapplication.BuildConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

fun postDelayed(delayInMillis: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(Runnable(block), delayInMillis)
}

fun LifecycleOwner.postDelayed(delayInMillis: Long, block: () -> Unit) {
    lifecycleScope.launch {
        delay(delayInMillis)
        block()
    }
}

fun okHttp(builder: OkHttpClient.Builder.() -> Unit) =
    OkHttpClient.Builder().apply {
        builder(this)
    }.build()


fun retrofit(builder: Retrofit.Builder.() -> Unit): Retrofit {
    return Retrofit.Builder().apply {
        builder(this)
    }.build()
}

fun loggingIntercept(block: HttpLoggingInterceptor.() -> Unit): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().apply { block(this) }
}

inline fun debugMode(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}