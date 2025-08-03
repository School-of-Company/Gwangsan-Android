package com.school_of_company.data.repository.notice

import com.school_of_company.model.notice.response.GetAllNoticeResponseModel
import com.school_of_company.model.notice.response.GetSpecificNoticeResponseModel
import com.school_of_company.network.datasource.notice.NoticeDataSource
import com.school_of_company.network.mapper.notice.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val noticeDataSource: NoticeDataSource
) : NoticeRepository {
    override fun getAllNotice(): Flow<List<GetAllNoticeResponseModel>> {
        return noticeDataSource.getAllNotice().map { it.map { list -> list.toModel() } }
    }

    override fun getSpecificNotice(noticeId: Long): Flow<GetSpecificNoticeResponseModel> {
        return noticeDataSource.getSpecificNotice(noticeId = noticeId).map { it.toModel() }
    }
}