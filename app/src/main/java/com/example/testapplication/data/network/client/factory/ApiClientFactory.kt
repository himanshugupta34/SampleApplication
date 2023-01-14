package com.example.testapplication.data.network.client.factory

import com.example.testapplication.data.network.client.ApiClient
import com.example.testapplication.data.network.qualifier.RetrofitBase
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClientFactory @Inject constructor(
    @RetrofitBase private val mRetrofitClient: Retrofit
) : IApiFactory {

    override val apiClient: ApiClient
        get() = mRetrofitClient.create(ApiClient::class.java)
}