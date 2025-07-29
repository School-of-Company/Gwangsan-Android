package com.school_of_company.data.repository.alert

import com.school_of_company.model.alert.response.GetAlertResponseModel
import com.school_of_company.model.alert.response.GetReadAlertModel
import kotlinx.coroutines.flow.Flow

interface AlertRepository {
    fun getAlert(): Flow<List<GetAlertResponseModel>>

    fun getUnReadAlert(): Flow<GetReadAlertModel>

    fun getReadAlert(alertId: Long): Flow<Unit>
}