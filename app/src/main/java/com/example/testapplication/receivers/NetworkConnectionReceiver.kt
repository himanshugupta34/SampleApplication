package com.example.testapplication.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.testapplication.extension.hasNetwork
import com.example.testapplication.interfaces.ConnectivityReceiverListener

class NetworkConnectionReceiver(
    private val connectivityReceiverListener: ConnectivityReceiverListener?
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.hasNetwork().let {
            connectivityReceiverListener?.onNetworkConnectionChanged(it)
        }
    }
}