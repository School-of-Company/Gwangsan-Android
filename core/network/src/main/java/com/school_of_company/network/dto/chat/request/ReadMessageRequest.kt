package com.school_of_company.network.dto.chat.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReadMessageRequest(
    @Json(name = "roomId") val roomId: Long,
    @Json(name = "lastMessageId") val lastMessageId: Long
)
