package com.example.testapplication.base

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.testapplication.extension.hasNetwork
import com.example.testapplication.receivers.NetworkConnectionReceiver
import com.example.testapplication.receivers.NetworkConnectionReceiver.Companion.connectivityReceiverListener

@Suppress("DEPRECATION")
abstract class BaseActivity : AppCompatActivity() {

    private val networkReceiver = NetworkConnectionReceiver()

    override fun onStart() {
        super.onStart()
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasNetwork().let {
            Log.i("NetworkConnReceiver", it.toString())
            if (connectivityReceiverListener != null)
                connectivityReceiverListener?.onNetworkConnectionChanged(it)
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkReceiver)
    }
}