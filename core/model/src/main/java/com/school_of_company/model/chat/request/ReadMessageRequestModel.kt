package com.school_of_company.model.chat.request

data class ReadMessageRequestModel(
    val roomId: Long,
    val lastMessageId: Long
)
