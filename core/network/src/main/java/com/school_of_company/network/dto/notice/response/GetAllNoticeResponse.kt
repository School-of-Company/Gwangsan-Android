package com.school_of_company.network.dto.notice.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetAllNoticeResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "images") val images: List<ImageDto>
)

@JsonClass(generateAdapter = true)
data class ImageDto(
    @Json(name = "imageId") val imageId: Long,
    @Json(name = "imageUrl") val imageUrl: String
)