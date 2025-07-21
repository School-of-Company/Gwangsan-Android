package com.school_of_company.network.mapper.chat.request

import com.school_of_company.model.chat.request.ReadMessageRequestModel
import com.school_of_company.network.dto.chat.request.ReadMessageRequest

fun ReadMessageRequestModel.toDto() = ReadMessageRequest(
    roomId = this.roomId,
    lastMessageId = this.lastMessageId
)