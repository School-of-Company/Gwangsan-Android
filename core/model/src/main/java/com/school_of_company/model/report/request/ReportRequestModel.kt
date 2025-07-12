package com.school_of_company.model.report.request

data class ReportRequestModel(
    val productId: Long,
    val reportType: String,
    val content: String
)