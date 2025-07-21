package com.school_of_company.model.chat.response

data class GetChatMessageResponseModel(
    val messageId: Long,
    val roomId: Long,
    val content: String,
    val messageType: String,
    val createdAt: String,
    val images: List<GetChatMessageImageModel>,
    val senderNickname: String,
    val senderId: String,
    val checked: Boolean,
    val isMine: Boolean
)

data class GetChatMessageImageModel(
    val imageId: Long,
    val imageUrl: String
)