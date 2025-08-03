package com.school_of_company.network.api

import com.school_of_company.network.dto.notice.response.GetAllNoticeResponse
import com.school_of_company.network.dto.notice.response.GetSpecificNoticeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NoticeAPI {

    @GET("/api/notice")
    suspend fun getAllNotice() : List<GetAllNoticeResponse>

    @GET("/api/notice/{noticeId}")
    suspend fun getSpecificNotice(
        @Path("noticeId") noticeId : Long
    ) : GetSpecificNoticeResponse
}