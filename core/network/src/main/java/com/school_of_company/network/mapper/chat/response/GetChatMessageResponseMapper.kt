package com.school_of_company.network.mapper.chat.response

import com.school_of_company.model.chat.response.GetChatMessageImageModel
import com.school_of_company.model.chat.response.GetChatMessageResponseModel
import com.school_of_company.network.dto.chat.response.GetChatMessageImage
import com.school_of_company.network.dto.chat.response.GetChatMessageResponse

fun GetChatMessageResponse.toModel() = GetChatMessageResponseModel(
    messageId = this.messageId,
    roomId = this.roomId,
    content = this.content,
    messageType = this.messageType,
    createdAt = this.createdAt,
    images = this.images.map { it.toModel() },
    senderNickname = this.senderNickname,
    senderId = this.senderId,
    checked = this.checked,
    isMine = this.isMine
)

fun GetChatMessageImage.toModel() = GetChatMessageImageModel(
    imageId = this.imageId,
    imageUrl = this.imageUrl
)