package com.school_of_company.chat.ui.mapper

import com.school_of_company.chat.ui.model.GetChatRoomResponseUi
import com.school_of_company.chat.ui.model.GetImageResponseUi
import com.school_of_company.chat.ui.model.GetMemberResponseUi
import com.school_of_company.chat.ui.model.GetProductResponseUi
import com.school_of_company.model.chat.response.GetChatRoomResponseModel
import com.school_of_company.model.chat.response.GetImageResponseModel
import com.school_of_company.model.chat.response.GetMemberResponseModel
import com.school_of_company.model.chat.response.GetProductResponseModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

/**
 * API 응답 → UI 모델 변환
 */

fun GetChatRoomResponseModel.toUi() = GetChatRoomResponseUi(
    roomId = this.roomId,
    member = this.member.toUi(),
    messageId = this.messageId,
    lastMessage = this.lastMessage,
    lastMessageType = this.lastMessageType,
    lastMessageTime = this.lastMessageTime,
    unreadMessageCount = this.unreadMessageCount,
    product = this.product.toUi()
)

fun GetProductResponseModel.toUi() = GetProductResponseUi(
    productId = this.productId,
    title = this.title,
    images = this.images?.map { it.toUi() }?.toPersistentList() ?: persistentListOf()
)

fun GetImageResponseModel.toUi() = GetImageResponseUi(
    imageId = this.imageId,
    imageUrl = this.imageUrl
)

fun GetMemberResponseModel.toUi() = GetMemberResponseUi(
    memberId = this.memberId,
    nickname = this.nickname
)
