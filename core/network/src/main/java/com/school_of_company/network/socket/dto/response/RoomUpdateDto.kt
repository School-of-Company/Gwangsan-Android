package com.school_of_company.network.socket.dto.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RoomUpdateDto(
    @Json(name = "roomId") val roomId: Long,
    @Json(name = "lastMessage") val lastMessage: String,
    @Json(name = "lastMessageType") val lastMessageType: String,
    @Json(name = "lastMessageTime") val lastMessageTime: String
)