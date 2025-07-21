package com.school_of_company.network.dto.chat.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetChatRoomResponse(
    @Json(name = "roomId") val roomId: Long,
    @Json(name = "member") val member: GetMemberResponse,
    @Json(name = "messageId") val messageId: Long,
    @Json(name = "lastMessage") val lastMessage: String,
    @Json(name = "lastMessageType") val lastMessageType: String,
    @Json(name = "lastMessageTime") val lastMessageTime: String,
    @Json(name = "unreadMessageCount") val unreadMessageCount: Long
)

@JsonClass(generateAdapter = true)
data class GetMemberResponse(
    @Json(name = "memberId") val memberId: Long,
    @Json(name = "nickname") val nickname: String,
)