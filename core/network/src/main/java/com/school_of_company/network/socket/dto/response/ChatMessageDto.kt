package com.school_of_company.network.socket.dto.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatMessageDto(
    @Json(name = "messageId") val messageId: Long,
    @Json(name = "roomId") val roomId: Long,
    @Json(name = "content") val content: String,
    @Json(name = "messageType") val messageType: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "images") val images: List<MessageImageDto>,
    @Json(name = "senderNickname") val senderNickname: String,
    @Json(name = "senderId") val senderId: String,
    @Json(name = "checked") val checked: Boolean,
    @Json(name = "isMine") val isMine: Boolean
)

@JsonClass(generateAdapter = true)
data class MessageImageDto(
    @Json(name = "imageId") val imageId: Long,
    @Json(name = "imageUrl") val imageUrl: String
)