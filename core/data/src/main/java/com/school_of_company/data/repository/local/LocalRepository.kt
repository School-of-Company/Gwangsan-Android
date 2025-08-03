package com.school_of_company.data.repository.local

interface LocalRepository {
    suspend fun savedDeviceToken(deviceToken: String)
    suspend fun getDeviceToken(): String
}