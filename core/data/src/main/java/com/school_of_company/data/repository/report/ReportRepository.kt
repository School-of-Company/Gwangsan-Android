package com.school_of_company.data.repository.report

import com.school_of_company.model.report.request.ReportRequestModel
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun report(body: ReportRequestModel) : Flow<Unit>
}