package com.school_of_company.gwangsan_android

import android.app.Application
import com.school_of_company.firebase.reporter.CrashReporter
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GwangsanApplication : Application() {

    @Inject
    lateinit var crashReporter: CrashReporter

    override fun onCreate() {
        super.onCreate()

        crashReporter.setupGlobalExceptionHandler(this)
    }
}