package com.school_of_company.network.socket.mapper.request

import com.school_of_company.network.socket.dto.request.SendMessageDto
import com.school_of_company.network.socket.model.request.SendMessage

fun SendMessage.toDto() = SendMessageDto(
    roomId = this.roomId,
    content = this.content,
    imageIds = this.imageIds,
    messageType = this.messageType
)