package com.school_of_company.network.mapper.review.response

import com.school_of_company.model.review.response.ImagesList
import com.school_of_company.model.review.response.ReviewResponseModel
import com.school_of_company.network.dto.review.response.ImagesListResponse
import com.school_of_company.network.dto.review.response.ReviewResponse
import kotlinx.collections.immutable.toPersistentList

fun ReviewResponse.toModel() : ReviewResponseModel =
    ReviewResponseModel(
        reviewerName = this.reviewerName,
        productId = this.productId,
        light = this.light,
        content = this.content,
        images = this.images.map { it.toModel() }.toPersistentList()
)

fun ImagesListResponse.toModel(): ImagesList =
    ImagesList(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )