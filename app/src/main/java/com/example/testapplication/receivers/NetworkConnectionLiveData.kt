package com.example.testapplication.receivers

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import com.example.testapplication.interfaces.ConnectivityReceiverListener
import java.lang.IllegalStateException

class NetworkConnectionLiveData(
    private val context: Context,
    private var connectivityReceiverListener: ConnectivityReceiverListener?
) : LiveData<Boolean?>() {

    private val networkReceiver by lazy { NetworkConnectionReceiver(connectivityReceiverListener) }

    private val networkReceiverListener = object : ConnectivityReceiverListener {
        override fun onNetworkConnectionChanged(isConnected: Boolean?) {
            postValue(isConnected)
        }
    }

    override fun onActive() {
        super.onActive()
        connectivityReceiverListener?.let {
            connectivityReceiverListener = networkReceiverListener
            context.registerReceiver(
                networkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
        }
    }

    override fun onInactive() {
        super.onInactive()
        try {
            if (connectivityReceiverListener != null)
                context.unregisterReceiver(networkReceiver)
            connectivityReceiverListener = null
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }
}