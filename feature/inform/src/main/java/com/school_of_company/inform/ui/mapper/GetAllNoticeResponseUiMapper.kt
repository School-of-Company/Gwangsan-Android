package com.school_of_company.inform.ui.mapper

import com.school_of_company.inform.ui.model.GetAllNoticeResponseUi
import com.school_of_company.inform.ui.model.ImageModelUi
import com.school_of_company.model.notice.response.GetAllNoticeResponseModel
import com.school_of_company.model.notice.response.ImageModel
import kotlinx.collections.immutable.toPersistentList

/**
 * API 응답 → UI 모델 변환
 */

fun GetAllNoticeResponseModel.toUi() : GetAllNoticeResponseUi =
    GetAllNoticeResponseUi(
        id = this.id,
        title = this.title,
        content = this.content,
        images = this.images.map { it.toUi() }.toPersistentList()
    )

fun ImageModel.toUi() : ImageModelUi =
    ImageModelUi(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )