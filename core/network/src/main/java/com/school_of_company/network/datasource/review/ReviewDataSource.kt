package com.school_of_company.network.datasource.review

import com.school_of_company.network.dto.review.request.ReviewRequest
import com.school_of_company.network.dto.review.response.ReviewResponse
import kotlinx.coroutines.flow.Flow

interface ReviewDataSource {
    fun postReview(body: ReviewRequest) : Flow<Unit>
    fun getMyWriteReview() : Flow<List<ReviewResponse>>
    fun getMyReview() : Flow<List<ReviewResponse>>
    fun getOtherReview(memberId: Long) : Flow<ReviewResponse>
}