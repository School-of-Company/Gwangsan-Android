package com.school_of_company.network.datasource.alert

import com.school_of_company.network.api.AlertAPI
import com.school_of_company.network.api.AuthAPI
import com.school_of_company.network.dto.alert.GetAlertResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlertDataSourceImpl @Inject constructor(
    private val alertAPI: AlertAPI
) : AlertDataSource{
    override fun getAlert(): Flow<List<GetAlertResponse>> =
        performApiRequest { alertAPI.getAlert() }
}