package com.school_of_company.network.datasource.report

import com.school_of_company.network.api.ReportAPI
import com.school_of_company.network.dto.report.request.ReportRequest
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReportDataSourceImpl @Inject constructor(
    private val reportApi: ReportAPI
) : ReportDataSource {
    override fun report(body: ReportRequest): Flow<Unit> =
        performApiRequest { reportApi.report(body = body) }
}