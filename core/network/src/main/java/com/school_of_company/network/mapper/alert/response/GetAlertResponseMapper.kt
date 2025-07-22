package com.school_of_company.network.mapper.alert.response

import com.school_of_company.model.alert.response.GetAlertImagesModel
import com.school_of_company.model.alert.response.GetAlertResponseModel
import com.school_of_company.network.dto.alert.GetAlertImages
import com.school_of_company.network.dto.alert.GetAlertResponse

fun GetAlertResponse.toModel() = GetAlertResponseModel(
    id = this.id,
    title = this.title,
    createdAt = this.createdAt,
    content = this.content,
    images = this.images.map { it.toModel() },
    alertType = this.alertType,
    sendMemberId = this.sendMemberId
)

fun GetAlertImages.toModel(): GetAlertImagesModel =
    GetAlertImagesModel(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )