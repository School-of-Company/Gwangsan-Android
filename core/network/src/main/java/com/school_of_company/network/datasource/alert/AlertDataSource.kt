package com.school_of_company.network.datasource.alert

import com.school_of_company.network.dto.alert.GetAlertResponse
import com.school_of_company.network.dto.auth.requset.SignUpRequest
import kotlinx.coroutines.flow.Flow

interface AlertDataSource {
    fun getAlert(): Flow<List<GetAlertResponse>>
}