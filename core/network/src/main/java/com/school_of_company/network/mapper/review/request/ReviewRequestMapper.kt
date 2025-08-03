package com.school_of_company.network.mapper.review.request

import com.school_of_company.model.review.request.ReviewRequestModel
import com.school_of_company.network.dto.review.request.ReviewRequest

fun ReviewRequestModel.toDto() : ReviewRequest =
    ReviewRequest(
        productId = this.productId,
        content = this.content,
        light = this.light
    )