package com.school_of_company.network.dto.review.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewRequest (
    @Json(name = "productId") val productId: Long,
    @Json(name = "content") val content: String,
    @Json(name = "light") val light: Int,
)