package com.school_of_company.network.dto.report.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReportRequest(
    @Json(name = "productId") val productId: Long,
    @Json(name = "reportType") val reportType: String,
    @Json(name = "content") val content: String
)
