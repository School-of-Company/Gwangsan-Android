package com.kim.datastore.di

import com.kim.datastore.datasource.AuthTokenDataSource
import com.kim.datastore.datasource.AuthTokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {
    @Binds
    abstract fun bindAuthTokenDataSource(
        authTokenDataSourceImpl: AuthTokenDataSourceImpl
    ): AuthTokenDataSource
}