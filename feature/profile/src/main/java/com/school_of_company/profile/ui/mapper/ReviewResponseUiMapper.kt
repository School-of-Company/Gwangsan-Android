package com.school_of_company.profile.ui.mapper

import com.school_of_company.model.review.response.ImagesList
import com.school_of_company.model.review.response.ReviewResponseModel
import com.school_of_company.profile.ui.model.ImagesListUi
import com.school_of_company.profile.ui.model.ReviewResponseUi
import kotlinx.collections.immutable.toPersistentList

/**
 * Socket 응답 → UI 모델 변환
 */

fun ReviewResponseModel.toUi() : ReviewResponseUi =
    ReviewResponseUi(
        productId = this.productId,
        content = this.content,
        light = this.light,
        reviewerName = this.reviewerName,
        images = this.images.map { it.toUi() }.toPersistentList()
    )

fun ImagesList.toUi() : ImagesListUi =
    ImagesListUi(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )