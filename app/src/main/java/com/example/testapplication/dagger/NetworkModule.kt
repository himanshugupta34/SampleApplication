package com.example.testapplication.dagger

import com.example.testapplication.BuildConfig
import com.example.testapplication.data.network.client.interceptor.NetworkConnectionInterceptor
import com.example.testapplication.data.network.qualifier.OkHttpClientBase
import com.example.testapplication.data.network.qualifier.RetrofitBase
import com.example.testapplication.extension.debugMode
import com.example.testapplication.extension.loggingIntercept
import com.example.testapplication.extension.okHttp
import com.example.testapplication.extension.retrofit
import com.example.testapplication.util.Constants.ApiBase.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHTTPLoggingInterceptor() = loggingIntercept {
        debugMode {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @OkHttpClientBase
    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
    ) = okHttp {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(2, TimeUnit.MINUTES)
        addInterceptor(networkConnectionInterceptor)
        addInterceptor(httpLoggingInterceptor)
    }

    @RetrofitBase
    @Provides
    @Singleton
    fun provideRetrofitClient(@OkHttpClientBase okHttpClient: OkHttpClient) = retrofit {
        baseUrl(BASE_URL)
        client(okHttpClient)
        addConverterFactory(GsonConverterFactory.create())
    }
}