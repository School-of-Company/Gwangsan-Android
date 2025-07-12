package com.school_of_company.network.datasource.report

import com.school_of_company.network.dto.report.request.ReportRequest
import kotlinx.coroutines.flow.Flow

interface ReportDataSource {

    fun report(body: ReportRequest) : Flow<Unit>
}