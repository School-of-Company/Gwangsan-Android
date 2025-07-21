package com.school_of_company.network.socket.model.request

data class SendMessage(
    val roomId: Long,
    val content: String,
    val imageIds: List<Long>,
    val messageType: String
)
