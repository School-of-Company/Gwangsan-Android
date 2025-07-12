package com.school_of_company.network.api

import com.school_of_company.network.dto.report.request.ReportRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportAPI {

    @POST("/api/report")
    suspend fun report(
        @Body body: ReportRequest
    )
}