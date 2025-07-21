package com.school_of_company.network.socket.model.response

data class ChatMessage(
    val messageId: Long,
    val roomId: Long,
    val content: String,
    val messageType: String,
    val createdAt: String,
    val images: List<MessageImage>,
    val senderNickname: String,
    val senderId: String,
    val checked: Boolean,
    val isMine: Boolean
)

data class MessageImage(
    val imageId: Long,
    val imageUrl: String
)