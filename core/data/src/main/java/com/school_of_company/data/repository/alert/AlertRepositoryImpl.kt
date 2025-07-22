package com.school_of_company.data.repository.alert

import com.school_of_company.model.alert.response.GetAlertResponseModel
import com.school_of_company.network.datasource.alert.AlertDataSource
import com.school_of_company.network.mapper.alert.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlertRepositoryImpl  @Inject constructor(
    private val alertDataSource: AlertDataSource
) : AlertRepository{
    override fun getAlert(): Flow<List<GetAlertResponseModel>> {
        return alertDataSource.getAlert().map { it.map { list -> list.toModel()} }
    }
}