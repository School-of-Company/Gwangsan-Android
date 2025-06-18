package com.kim.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.kim.datastore.AuthToken
import com.kim.datastore.serializer.AuthTokenSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideAuthTokenDataStore(
        @ApplicationContext context: Context,
        authTokenSerializer: AuthTokenSerializer
    ): DataStore<AuthToken> =
        DataStoreFactory.create(
            serializer = authTokenSerializer,
        ) {
            context.dataStoreFile("authToken.pb")
        }
}