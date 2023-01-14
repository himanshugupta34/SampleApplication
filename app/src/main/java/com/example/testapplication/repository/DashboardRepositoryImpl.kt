package com.example.testapplication.repository

import com.example.testapplication.base.BaseRepository
import com.example.testapplication.data.network.client.factory.IApiFactory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepositoryImpl @Inject constructor(
    private val apiClientFactory: IApiFactory
) : BaseRepository(), IDashboardRepository {

    override suspend fun getJoke(): Flow<String> =
        makeSafeRequestForFlow { apiClientFactory.apiClient.getJoke() }
}