package com.school_of_company.network.mapper.chat.response

import com.school_of_company.model.chat.response.GetChatRoomResponseModel
import com.school_of_company.model.chat.response.GetImageResponseModel
import com.school_of_company.model.chat.response.GetMemberResponseModel
import com.school_of_company.model.chat.response.GetProductResponseModel
import com.school_of_company.network.dto.chat.response.GetChatRoomResponse
import com.school_of_company.network.dto.chat.response.GetImageResponse
import com.school_of_company.network.dto.chat.response.GetMemberResponse
import com.school_of_company.network.dto.chat.response.GetProductResponse
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

fun GetChatRoomResponse.toModel() = GetChatRoomResponseModel(
    roomId = this.roomId,
    member = this.member.toModel(),
    messageId = this.messageId,
    lastMessage = this.lastMessage,
    lastMessageType = this.lastMessageType,
    lastMessageTime = this.lastMessageTime,
    unreadMessageCount = this.unreadMessageCount,
    product = this.product.toModel()
)

fun GetProductResponse.toModel() = GetProductResponseModel(
    productId = this.productId,
    title = this.title,
    images = images?.map { it.toModel() }?.toPersistentList() ?: persistentListOf(),
)

fun GetImageResponse.toModel() = GetImageResponseModel(
    imageId = this.imageId,
    imageUrl = this.imageUrl
)

fun GetMemberResponse.toModel() = GetMemberResponseModel(
    memberId = this.memberId,
    nickname = this.nickname
)