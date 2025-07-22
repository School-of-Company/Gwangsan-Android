package com.school_of_company.network.mapper.report.request

import com.school_of_company.model.report.request.ReportRequestModel
import com.school_of_company.network.dto.report.request.ReportRequest

fun ReportRequestModel.toDto() : ReportRequest =
    ReportRequest(
        sourceId = this.sourceId,
        reportType = this.reportType,
        content = this.content
    )