package com.school_of_company.network.mapper.chat.response

import com.school_of_company.model.chat.response.ChatMessageResponseModel
import com.school_of_company.model.chat.response.GetChatMessageImageModel
import com.school_of_company.model.chat.response.GetChatMessagesResponseModel
import com.school_of_company.model.chat.response.TradeImageModel
import com.school_of_company.model.chat.response.TradeProductModel
import com.school_of_company.network.dto.chat.response.ChatMessageResponse
import com.school_of_company.network.dto.chat.response.GetChatMessageImage
import com.school_of_company.network.dto.chat.response.GetChatMessagesResponse
import com.school_of_company.network.dto.chat.response.TradeImage
import com.school_of_company.network.dto.chat.response.TradeProduct

fun GetChatMessagesResponse.toModel() = GetChatMessagesResponseModel(
    messages = this.messages.map { it.toModel() },
    product = this.product.toModel()
)

fun ChatMessageResponse.toModel() = ChatMessageResponseModel(
    messageId = this.messageId,
    roomId = this.roomId,
    content = this.content,
    messageType = this.messageType,
    createdAt = this.createdAt,
    images = this.images?.map { it.toModel() },
    senderNickname = this.senderNickname,
    senderId = this.senderId,
    checked = this.checked,
    isMine = this.isMine
)


fun GetChatMessageImage.toModel() = GetChatMessageImageModel(
    imageId = this.imageId,
    imageUrl = this.imageUrl
)

fun TradeProduct.toModel() = TradeProductModel(
    id = this.id,
    title = this.title,
    images = this.images?.map { it.toModel() },
    createdAt = this.createdAt,
    isSeller = this.isSeller,
    isCompletable = this.isCompletable
)

fun TradeImage.toModel () = TradeImageModel(
    imageId = this.imageId,
    imageUrl = this.imageUrl
)