package com.school_of_company.chat.ui.mapper

import com.school_of_company.chat.ui.model.ChatMessageUi
import com.school_of_company.chat.ui.model.MessageImageUi
import com.school_of_company.network.socket.model.response.ChatMessage
import com.school_of_company.network.socket.model.response.MessageImage
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

/**
 * Socket 응답 → UI 모델 변환
 */

fun ChatMessage.toUi() = ChatMessageUi(
    messageId = this.messageId,
    roomId = this.roomId,
    content = this.content,
    messageType = this.messageType,
    createdAt = this.createdAt,
    images = this.images?.map { it.toUi() }?.toPersistentList() ?: persistentListOf(),
    senderNickname = this.senderNickname,
    senderId = this.senderId,
    checked = this.checked,
    isMine = this.isMine
)

fun MessageImage.toUi() = MessageImageUi(
    imageId = this.imageId,
    imageUrl = this.imageUrl
)