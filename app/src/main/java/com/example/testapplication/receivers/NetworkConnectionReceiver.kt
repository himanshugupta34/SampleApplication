package com.example.testapplication.receivers

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.testapplication.extension.hasNetwork
import com.example.testapplication.interfaces.ConnectivityReceiverListener

class NetworkConnectionReceiver : BroadcastReceiver() {

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.hasNetwork().let {
            Log.i("NetworkConnReceiver", it.toString())
            if (connectivityReceiverListener != null)
                connectivityReceiverListener?.onNetworkConnectionChanged(it)
        }
    }
}