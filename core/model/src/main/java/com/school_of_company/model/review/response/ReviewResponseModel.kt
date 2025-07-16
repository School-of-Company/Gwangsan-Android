package com.school_of_company.model.review.response

data class ReviewResponseModel(
    val productId: Long,
    val content: String,
    val light: Int,
    val reviewerName: String,
    val images: List<ImagesList>
)

data class ImagesList(
    val imageId: Long,
    val imageUrl: String
)