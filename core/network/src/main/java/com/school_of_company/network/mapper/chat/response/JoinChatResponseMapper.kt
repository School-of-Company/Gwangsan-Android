package com.school_of_company.network.mapper.chat.response

import com.school_of_company.model.chat.response.JoinChatResponseModel
import com.school_of_company.network.dto.chat.response.JoinChatResponse

fun JoinChatResponse.toModel() = JoinChatResponseModel(
    roomId = this.roomId
)