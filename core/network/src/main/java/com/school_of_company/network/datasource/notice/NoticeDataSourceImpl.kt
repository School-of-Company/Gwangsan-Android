package com.school_of_company.network.datasource.notice

import com.school_of_company.network.api.NoticeAPI
import com.school_of_company.network.dto.notice.response.GetAllNoticeResponse
import com.school_of_company.network.dto.notice.response.GetSpecificNoticeResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoticeDataSourceImpl @Inject constructor(
    private val noticeApi: NoticeAPI
) : NoticeDataSource {
    override fun getAllNotice(): Flow<List<GetAllNoticeResponse>> =
        performApiRequest { noticeApi.getAllNotice() }

    override fun getSpecificNotice(noticeId: Long): Flow<GetSpecificNoticeResponse> =
        performApiRequest { noticeApi.getSpecificNotice(noticeId = noticeId) }
}