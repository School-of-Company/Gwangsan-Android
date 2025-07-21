package com.school_of_company.network.socket.dto.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendMessageDto(
    @Json(name = "roomId") val roomId: Long,
    @Json(name = "content") val content: String,
    @Json(name = "imageIds") val imageIds: List<Long>,
    @Json(name = "messageType") val messageType: String
)