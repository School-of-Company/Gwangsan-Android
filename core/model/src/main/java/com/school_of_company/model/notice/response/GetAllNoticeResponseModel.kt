package com.school_of_company.model.notice.response

data class GetAllNoticeResponseModel(
    val id: Long,
    val title: String,
    val content: String,
    val images: List<ImageModel>
)

data class ImageModel(
    val imageId: Long,
    val imageUrl: String
)
