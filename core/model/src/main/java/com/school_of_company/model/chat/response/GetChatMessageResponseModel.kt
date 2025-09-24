package com.school_of_company.model.chat.response

data class GetChatMessagesResponseModel(
    val messages: List<ChatMessageResponseModel>,
    val product: TradeProductModel
)
data class ChatMessageResponseModel(
    val messageId: Long,
    val roomId: Long,
    val content: String,
    val messageType: String,
    val createdAt: String,
    val images: List<GetChatMessageImageModel>?,
    val senderNickname: String,
    val senderId: Long,
    val checked: Boolean,
    val isMine: Boolean
)

data class GetChatMessageImageModel(
    val imageId: Long,
    val imageUrl: String
)

data class TradeImageModel(
    val imageId: Long,
    val imageUrl: String
)
data class TradeProductModel(
    val id: Long,
    val title: String,
    val images: List<TradeImageModel>?,
    val createdAt: String?,          // nullable
    val isSeller: Boolean,
    val isCompletable: Boolean,
    val isCompleted: Boolean,
)