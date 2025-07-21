package com.school_of_company.network.dto.chat.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JoinChatResponse(
    @Json(name = "roomId") val roomId: Long
)
