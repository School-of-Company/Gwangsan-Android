package com.school_of_company.local.datasource

interface LocalDeviceDataSource {
    suspend fun savedDeviceToken(deviceToken: String)
    suspend fun getDeviceToken(): String
}