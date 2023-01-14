package com.example.testapplication.dagger

import com.example.testapplication.data.network.client.factory.ApiClientFactory
import com.example.testapplication.data.network.client.factory.IApiFactory
import com.example.testapplication.repository.DashboardRepositoryImpl
import com.example.testapplication.repository.IDashboardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideIApiClientFactory(factory: ApiClientFactory): IApiFactory {
        return factory
    }

    @Provides
    @Singleton
    fun provideIDashboardRepository(dashboardRepositoryImpl: DashboardRepositoryImpl): IDashboardRepository {
        return dashboardRepositoryImpl
    }
}