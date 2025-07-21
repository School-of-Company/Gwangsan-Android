package com.school_of_company.model.chat.response

data class GetChatRoomResponseModel(
    val roomId: Long,
    val member: GetMemberResponseModel,
    val messageId: Long,
    val lastMessage: String,
    val lastMessageType: String,
    val lastMessageTime: String,
    val unreadMessageCount: Long
)

data class GetMemberResponseModel(
    val memberId: Long,
    val nickname: String
)