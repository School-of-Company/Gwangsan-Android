package com.school_of_company.network.api

import com.school_of_company.network.dto.review.request.ReviewRequest
import com.school_of_company.network.dto.review.response.ReviewResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewAPI {
    @POST("/api/review")
    suspend fun postReview(
        @Body body: ReviewRequest
    )

    @GET("/api/review")
    suspend fun getMyWriteReview(
        @Query("type") type: String? = null,
        @Query("mode") mode: String? = null
    ) : ReviewResponse

    @GET("/api/review/current")
    suspend fun getMyReview() : List<ReviewResponse>

    @GET("/api/review/{memberId}")
    suspend fun getOtherReview(
        @Path("memberId") memberId: Long
    ) : ReviewResponse
}