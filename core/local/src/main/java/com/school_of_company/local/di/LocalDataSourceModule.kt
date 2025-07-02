package com.school_of_company.local.di

import com.school_of_company.local.datasource.LocalDeviceDataSource
import com.school_of_company.local.datasource.LocalDeviceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalDeviceDataSource(
        localDeviceDataSourceImpl: LocalDeviceDataSourceImpl
    ): LocalDeviceDataSource
}