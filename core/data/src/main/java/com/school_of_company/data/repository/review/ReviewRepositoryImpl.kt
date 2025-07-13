package com.school_of_company.data.repository.review

import com.school_of_company.data.repository.report.ReportRepository
import com.school_of_company.model.report.request.ReportRequestModel
import com.school_of_company.model.review.request.ReviewRequestModel
import com.school_of_company.model.review.response.ReviewResponseModel
import com.school_of_company.network.datasource.report.ReportDataSource
import com.school_of_company.network.datasource.review.ReviewDataSource
import com.school_of_company.network.mapper.report.request.toDto
import com.school_of_company.network.mapper.review.request.toDto
import com.school_of_company.network.mapper.review.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewDataSource: ReviewDataSource
) : ReviewRepository {
    override fun getMyReview(): Flow<ReviewResponseModel> {
        return reviewDataSource.getMyReview().map { it.toModel() }
    }

    override fun getOtherReview(memberId: Long): Flow<ReviewResponseModel> {
        return reviewDataSource.getOtherReview(memberId = memberId).map { it.toModel() }
    }

    override fun postReview(body: ReviewRequestModel): Flow<Unit> {
        return reviewDataSource.postReview(body = body.toDto())
    }

    override fun getMyWriteReview(type: String?, mode: String?): Flow<ReviewResponseModel> {
       return reviewDataSource.getMyWriteReview(
           type = type,
           mode = mode
       ).map { it.toModel() }
    }

}