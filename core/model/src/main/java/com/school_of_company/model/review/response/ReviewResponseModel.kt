package com.school_of_company.model.review.response

data class ReviewResponseModel(
    val productId: Long,
    val content: String,
    val light: Int,
    val reviewerName: String,
)