package com.example.testapplication.dagger

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
    fun provideIDashboardRepository(): IDashboardRepository = DashboardRepositoryImpl()
}