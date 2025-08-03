package com.school_of_company.network.socket.mapper.response

import com.school_of_company.network.socket.dto.response.ChatMessageDto
import com.school_of_company.network.socket.dto.response.MessageImageDto
import com.school_of_company.network.socket.model.response.ChatMessage
import com.school_of_company.network.socket.model.response.MessageImage
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

fun ChatMessageDto.toModel() = ChatMessage(
    messageId = this.messageId,
    roomId = this.roomId,
    content = this.content,
    messageType = this.messageType,
    createdAt = this.createdAt,
    images = images?.map { it.toModel() }?.toPersistentList() ?: persistentListOf(),
    senderNickname = this.senderNickname,
    senderId = this.senderId,
    checked = this.checked,
    isMine = this.isMine
)

fun MessageImageDto.toModel() = MessageImage(
    imageId = this.imageId,
    imageUrl = this.imageUrl
)