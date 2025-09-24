package com.school_of_company.network.socket.mapper.response

import com.school_of_company.network.socket.dto.response.ChatMessageDto
import com.school_of_company.network.socket.dto.response.GetChatMessagesResponseDto
import com.school_of_company.network.socket.dto.response.MessageImageDto
import com.school_of_company.network.socket.dto.response.TradeImageDto
import com.school_of_company.network.socket.dto.response.TradeProductDto
import com.school_of_company.network.socket.model.response.ChatMessage
import com.school_of_company.network.socket.model.response.GetChatResponse
import com.school_of_company.network.socket.model.response.MessageImage
import com.school_of_company.network.socket.model.response.TradeImage
import com.school_of_company.network.socket.model.response.TradeProduct
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

fun GetChatMessagesResponseDto.toModel() = GetChatResponse(
    message = this.messages.map { it.toModel() },
    product = this.product.toModel()
)

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

fun TradeImageDto.toModel() = TradeImage(
    imageId = this.imageId,
    imageUrl = this.imageUrl
)

fun TradeProductDto.toModel() = TradeProduct(
    id = this.id,
    title = this.title,
    images = images?.map { it.toModel() }?.toPersistentList() ?:persistentListOf(),
    createdAt = this.createdAt,
    isSeller = this.isSeller,
    isCompletable = this.isCompletable,
    isCompleted = this.isCompleted


)