package com.school_of_company.network.mapper.review.response

import com.school_of_company.model.review.response.ReviewResponseModel
import com.school_of_company.network.dto.review.response.ReviewResponse

fun ReviewResponse.toModel() : ReviewResponseModel =
    ReviewResponseModel(
        reviewerName = this.reviewerName,
        productId = this.productId,
        light = this.light,
        content = this.content
)
