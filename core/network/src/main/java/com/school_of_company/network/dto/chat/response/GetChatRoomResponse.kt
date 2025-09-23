package com.school_of_company.network.dto.chat.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass




@JsonClass(generateAdapter = true)
data class GetChatRoomResponse(
    @Json(name = "roomId") val roomId: Long,
    @Json(name = "member") val member: GetMemberResponse,

    @Json(name = "messageId") val messageId: Long? = null,
    @Json(name = "lastMessage") val lastMessage: String? = null,
    @Json(name = "lastMessageType") val lastMessageType: String? = null,
    @Json(name = "lastMessageTime") val lastMessageTime: String? = null,

    @Json(name = "unreadMessageCount") val unreadMessageCount: Long,
    @Json(name = "product") val product: GetProductResponse
)

@JsonClass(generateAdapter = true)
data class GetProductResponse(
    @Json(name = "productId") val productId: Long,
    @Json(name = "title") val title: String,
    @Json(name = "images") val images: List<GetImageResponse>?,
)

@JsonClass(generateAdapter = true)
data class GetImageResponse(
    @Json(name = "imageId") val imageId: Long,
    @Json(name = "imageUrl") val imageUrl: String,
)

@JsonClass(generateAdapter = true)
data class GetMemberResponse(
    @Json(name = "memberId") val memberId: Long,
    @Json(name = "nickname") val nickname: String,
)
