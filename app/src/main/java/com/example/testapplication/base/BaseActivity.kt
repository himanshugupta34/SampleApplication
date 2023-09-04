package com.example.testapplication.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.testapplication.interfaces.ConnectivityReceiverListener
import com.example.testapplication.receivers.NetworkConnectionLiveData

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var networkConnectionLiveData: NetworkConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkConnectionLiveData = NetworkConnectionLiveData(this, connectivityReceiverListener)
        networkConnectionLiveData.observe(this, networkObserver)
    }

    private val networkObserver = Observer<Boolean?> { isConnected ->
        onNetworkConnectionChanged(isConnected)
    }

    override fun onDestroy() {
        super.onDestroy()
        networkConnectionLiveData.removeObserver(networkObserver)
    }

    abstract fun onNetworkConnectionChanged(isConnected: Boolean?)
    abstract val connectivityReceiverListener: ConnectivityReceiverListener
}