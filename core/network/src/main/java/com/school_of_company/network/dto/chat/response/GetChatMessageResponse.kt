package com.school_of_company.network.dto.chat.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetChatMessageResponse(
    @Json(name = "messageId") val messageId: Long,
    @Json(name = "roomId") val roomId: Long,
    @Json(name = "content") val content: String,
    @Json(name = "messageType") val messageType: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "images") val images: List<GetChatMessageImage>?,
    @Json(name = "senderNickname") val senderNickname: String,
    @Json(name = "senderId") val senderId: String,
    @Json(name = "checked") val checked: Boolean,
    @Json(name = "isMine") val isMine: Boolean
)

@JsonClass(generateAdapter = true)
data class GetChatMessageImage(
    @Json(name = "imageId") val imageId: Long,
    @Json(name = "imageUrl") val imageUrl: String
)