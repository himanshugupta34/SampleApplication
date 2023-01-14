package com.example.testapplication.data.network.client

import com.example.testapplication.util.Constants.ApiExtensions.GET_JOKE
import retrofit2.Response
import retrofit2.http.*

interface ApiClient {

    @GET(GET_JOKE)
    suspend fun getJoke(): Response<String>
}