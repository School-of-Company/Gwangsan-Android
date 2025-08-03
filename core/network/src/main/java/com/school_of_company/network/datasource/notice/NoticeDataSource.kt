package com.school_of_company.network.datasource.notice

import com.school_of_company.network.dto.notice.response.GetAllNoticeResponse
import com.school_of_company.network.dto.notice.response.GetSpecificNoticeResponse
import kotlinx.coroutines.flow.Flow

interface NoticeDataSource {

    fun getAllNotice() : Flow<List<GetAllNoticeResponse>>
    fun getSpecificNotice(noticeId: Long) : Flow<GetSpecificNoticeResponse>
}