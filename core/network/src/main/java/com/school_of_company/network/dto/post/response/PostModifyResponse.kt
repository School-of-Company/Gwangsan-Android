package com.school_of_company.network.dto.post.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostModifyResponse (
    @Json(name = "message") val message: String
)
