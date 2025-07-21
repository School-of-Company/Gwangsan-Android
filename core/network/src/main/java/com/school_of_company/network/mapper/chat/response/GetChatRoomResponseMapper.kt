package com.school_of_company.network.mapper.chat.response

import com.school_of_company.model.chat.response.GetChatRoomResponseModel
import com.school_of_company.model.chat.response.GetMemberResponseModel
import com.school_of_company.network.dto.chat.response.GetChatRoomResponse
import com.school_of_company.network.dto.chat.response.GetMemberResponse

fun GetChatRoomResponse.toModel() = GetChatRoomResponseModel(
    roomId = this.roomId,
    member = this.member.toModel(),
    messageId = this.messageId,
    lastMessage = this.lastMessage,
    lastMessageType = this.lastMessageType,
    lastMessageTime = this.lastMessageTime,
    unreadMessageCount = this.unreadMessageCount
)

fun GetMemberResponse.toModel() = GetMemberResponseModel(
    memberId = this.memberId,
    nickname = this.nickname
)