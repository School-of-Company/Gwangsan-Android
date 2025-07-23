package com.school_of_company.model.report.request

data class ReportRequestModel(
    val sourceId: Long,
    val reportType: String,
    val content: String
)