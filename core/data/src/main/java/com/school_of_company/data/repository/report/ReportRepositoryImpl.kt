package com.school_of_company.data.repository.report

import com.school_of_company.model.report.request.ReportRequestModel
import com.school_of_company.network.datasource.report.ReportDataSource
import com.school_of_company.network.mapper.report.request.toDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDataSource: ReportDataSource
) : ReportRepository {
    override fun report(body: ReportRequestModel): Flow<Unit> {
        return reportDataSource.report(body = body.toDto())
    }
}