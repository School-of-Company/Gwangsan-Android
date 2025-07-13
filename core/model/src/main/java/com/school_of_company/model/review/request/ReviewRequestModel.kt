package com.school_of_company.model.review.request

data class ReviewRequestModel(
    val productId: Long,
    val content: String,
    val light: Int,
)