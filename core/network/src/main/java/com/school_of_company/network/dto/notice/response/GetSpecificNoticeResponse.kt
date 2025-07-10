package com.school_of_company.network.dto.notice.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetSpecificNoticeResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "place") val place: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "role") val role: String,
    @Json(name = "images") val images: List<SpecificNoticeImageDto>
)

@JsonClass(generateAdapter = true)
data class SpecificNoticeImageDto(
    @Json(name = "imageId") val imageId: Long,
    @Json(name = "imageUrl") val imageUrl: String
)
