package com.school_of_company.network.mapper.notice.response

import com.school_of_company.model.notice.response.GetSpecificNoticeResponseModel
import com.school_of_company.model.notice.response.SpecificNoticeImageModel
import com.school_of_company.network.dto.notice.response.GetSpecificNoticeResponse
import com.school_of_company.network.dto.notice.response.SpecificNoticeImageDto

fun GetSpecificNoticeResponse.toModel(): GetSpecificNoticeResponseModel =
    GetSpecificNoticeResponseModel(
        id = id,
        title = title,
        content = content,
        place = place,
        createdAt = createdAt,
        role = role,
        images = images.map { it.toModel() }
    )

fun SpecificNoticeImageDto.toModel(): SpecificNoticeImageModel =
    SpecificNoticeImageModel(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )