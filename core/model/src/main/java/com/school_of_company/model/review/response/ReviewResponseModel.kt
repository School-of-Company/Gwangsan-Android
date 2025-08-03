package com.school_of_company.model.review.response

import kotlinx.collections.immutable.PersistentList

data class ReviewResponseModel(
    val productId: Long,
    val content: String,
    val light: Int,
    val reviewerName: String,
    val images: PersistentList<ImagesList>
)

data class ImagesList(
    val imageId: Long,
    val imageUrl: String
)