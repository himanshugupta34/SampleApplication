package com.example.testapplication.data.network.client.interceptor

import android.content.Context
import com.example.testapplication.NoNetworkException
import com.example.testapplication.R
import com.example.testapplication.extension.hasNetwork
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class NetworkConnectionInterceptor @Inject constructor(
    @ApplicationContext val context: Context
) : Interceptor {

    @Throws(NoNetworkException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.hasNetwork())
            throw NoNetworkException(context.getString(
                R.string.no_internet
            ))
        return chain.proceed(chain.request())
    }
}