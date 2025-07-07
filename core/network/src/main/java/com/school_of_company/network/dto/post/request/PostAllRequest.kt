package com.school_of_company.network.dto.post.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostAllRequest(
    @Json(name = "type") val type: String,
    @Json(name = "mode") val mode: String,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "gwangsan") val gwangsan: Int,
    @Json(name = "imageIds") val imageIds: List<Long>
)
