package com.school_of_company.model.alert.response

import com.school_of_company.model.enum.AlertType

data class GetAlertResponseModel(
    val id: Long,
    val title: String,
    val content: String,
    val sendMemberId: Long? = null,
    val createdAt :String,
    val alertType: AlertType,
    val sourceId: Long,
    val images: List<GetAlertImagesModel>
)

data class GetAlertImagesModel(
    val imageId: Long,
    val imageUrl: String
)