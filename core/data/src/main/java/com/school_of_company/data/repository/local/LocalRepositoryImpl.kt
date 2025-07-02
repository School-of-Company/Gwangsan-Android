package com.school_of_company.data.repository.local

import com.school_of_company.local.datasource.LocalDeviceDataSource
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val localDeviceDataSource: LocalDeviceDataSource
) : LocalRepository {
    override suspend fun savedDeviceToken(deviceToken: String) {
        localDeviceDataSource.savedDeviceToken(deviceToken = deviceToken)
    }

    override suspend fun getDeviceToken(): String {
        return localDeviceDataSource.getDeviceToken()
    }
}