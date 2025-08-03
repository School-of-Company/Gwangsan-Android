package com.school_of_company.firebase.di

import com.school_of_company.firebase.reporter.CrashReporter
import com.school_of_company.network.api.WebhookAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideCrashReporter(webhookAPI: WebhookAPI): CrashReporter {
        return CrashReporter(webhookAPI)
    }
}