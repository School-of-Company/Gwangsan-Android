package com.school_of_company.main.ui.mapper

import com.school_of_company.main.ui.model.GetAlertImagesUi
import com.school_of_company.main.ui.model.GetAlertResponseUi
import com.school_of_company.model.alert.response.GetAlertImagesModel
import com.school_of_company.model.alert.response.GetAlertResponseModel
import kotlinx.collections.immutable.toPersistentList

/**
 * API 응답 → UI 모델 변환
 */


fun GetAlertResponseModel.toUi() : GetAlertResponseUi =
    GetAlertResponseUi(
        id = this.id,
        title = this.title,
        content = this.content,
        sendMemberId = this.sendMemberId,
        createdAt = this.createdAt,
        alertType = this.alertType,
        sourceId = this.sourceId,
        images = this.images.map { it.toUi() }.toPersistentList()
    )

fun GetAlertImagesModel.toUi() : GetAlertImagesUi =
    GetAlertImagesUi(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )