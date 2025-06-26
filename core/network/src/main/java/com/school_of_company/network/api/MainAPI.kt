package com.school_of_company.network.api

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.network.dto.main.reponse.MainListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MainAPI {
    @GET("/api/post")
    suspend fun allPostGet(
        @Query("type") type: Type,
        @Query("mode") mode: Mode,
    ): List<MainListResponse>
}