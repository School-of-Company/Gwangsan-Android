package com.school_of_company.network.api

import com.school_of_company.network.dto.alert.GetAlertResponse
import retrofit2.http.GET

interface AlertAPI {
    @GET("api/alert")
    suspend fun getAlert(): List<GetAlertResponse>
}