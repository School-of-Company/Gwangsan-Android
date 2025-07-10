package com.school_of_company.data.repository.notice

import com.school_of_company.model.notice.response.GetAllNoticeResponseModel
import com.school_of_company.model.notice.response.GetSpecificNoticeResponseModel
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {

    fun getAllNotice() : Flow<List<GetAllNoticeResponseModel>>
    fun getSpecificNotice(noticeId : Long) : Flow<List<GetSpecificNoticeResponseModel>>
}