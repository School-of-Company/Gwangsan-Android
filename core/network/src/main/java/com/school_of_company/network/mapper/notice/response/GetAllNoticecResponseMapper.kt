package com.school_of_company.network.mapper.notice.response

import com.school_of_company.model.notice.response.GetAllNoticeResponseModel
import com.school_of_company.model.notice.response.ImageModel
import com.school_of_company.network.dto.notice.response.GetAllNoticeResponse
import com.school_of_company.network.dto.notice.response.ImageDto
import kotlinx.collections.immutable.toPersistentList

fun GetAllNoticeResponse.toModel(): GetAllNoticeResponseModel =
    GetAllNoticeResponseModel(
        id = this.id,
        title = this.title,
        content = this.content,
        images = this.images.map { it.toModel() }.toPersistentList()
    )

fun ImageDto.toModel(): ImageModel =
    ImageModel(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )