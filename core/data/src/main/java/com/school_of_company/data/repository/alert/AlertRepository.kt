package com.school_of_company.data.repository.alert

import com.school_of_company.model.alert.response.GetAlertResponseModel
import kotlinx.coroutines.flow.Flow

interface AlertRepository {
    fun getAlert(): Flow<List<GetAlertResponseModel>>
}