package com.example.testapplication.repository

import kotlinx.coroutines.flow.Flow

interface IDashboardRepository {

    suspend fun getJoke(): Flow<String>
}