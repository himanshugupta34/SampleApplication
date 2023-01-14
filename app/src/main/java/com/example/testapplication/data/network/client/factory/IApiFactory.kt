package com.example.testapplication.data.network.client.factory

import com.example.testapplication.data.network.client.ApiClient

interface IApiFactory {
    val apiClient: ApiClient
}