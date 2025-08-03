package com.school_of_company.network.api

import com.school_of_company.network.dto.alert.GetAlertResponse
import com.school_of_company.network.dto.alert.GetReadAlert
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AlertAPI {
    @GET("api/alert")
    suspend fun getAlert(): List<GetAlertResponse>

    @GET("api/alert/unread")
    suspend fun getUnReadAlert(): GetReadAlert

    @PATCH("api/alert/read/{alert_id}")
    suspend fun getReadAlert(
        @Path("alert_id") alertId: Long
    )
}