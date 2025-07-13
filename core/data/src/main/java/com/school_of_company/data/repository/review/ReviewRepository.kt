package com.school_of_company.data.repository.review

import com.school_of_company.model.review.request.ReviewRequestModel
import com.school_of_company.model.review.response.ReviewResponseModel
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    fun postReview(body: ReviewRequestModel) : Flow<Unit>
    fun getMyWriteReview(
        type: String? = null,
        mode: String? = null
    ) : Flow<ReviewResponseModel>
    fun getMyReview() : Flow<List<ReviewResponseModel>>
    fun getOtherReview(memberId: Long) : Flow<ReviewResponseModel>
}