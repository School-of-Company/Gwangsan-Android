package com.school_of_company.network.datasource.review

import com.school_of_company.network.api.ReviewAPI
import com.school_of_company.network.dto.review.request.ReviewRequest
import com.school_of_company.network.dto.review.response.ReviewResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewDataSourceImpl @Inject constructor(
    private val reviewApi: ReviewAPI
) : ReviewDataSource {

    override fun getMyReview(): Flow<ReviewResponse>  =
        performApiRequest { reviewApi.getMyReview() }

    override fun postReview(body: ReviewRequest): Flow<Unit> =
        performApiRequest { reviewApi.postReview(body = body) }

    override fun getMyWriteReview(type: String?, mode: String?): Flow<ReviewResponse> =
        performApiRequest { reviewApi.getMyWriteReview(
            type = type,
            mode = mode
        ) }

    override fun getOtherReview(memberId: Long): Flow<ReviewResponse> =
        performApiRequest { reviewApi.getOtherReview(memberId = memberId) }
}
