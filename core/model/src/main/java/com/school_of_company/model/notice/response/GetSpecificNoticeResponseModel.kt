package com.school_of_company.model.notice.response

data class GetSpecificNoticeResponseModel(
    val id: Long,
    val title: String,
    val content: String,
    val place: String,
    val createdAt: String,
    val role: String,
    val images: List<SpecificNoticeImageModel>
)

data class SpecificNoticeImageModel(
    val imageId: Long,
    val imageUrl: String
)