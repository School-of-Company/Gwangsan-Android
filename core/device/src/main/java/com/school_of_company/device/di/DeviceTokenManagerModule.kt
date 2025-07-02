package com.school_of_company.device.di

import com.school_of_company.data.repository.local.LocalRepository
import com.school_of_company.device.manager.DeviceTokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeviceTokenManagerModule {

    @Provides
    @Singleton
    fun provideDeviceTokenManager(localRepository: LocalRepository): DeviceTokenManager {
        return DeviceTokenManager(localRepository = localRepository)
    }
}