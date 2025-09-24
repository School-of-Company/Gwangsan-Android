package com.school_of_company.network.socket.model.response

import kotlinx.collections.immutable.PersistentList



data class GetChatResponse(
    val message: List<ChatMessage>,
    val product: TradeProduct
)

data class ChatMessage(
    val messageId: Long,
    val roomId: Long,
    val content: String,
    val messageType: String,
    val createdAt: String,
    val images: PersistentList<MessageImage>?,
    val senderNickname: String,
    val senderId: Long,
    val checked: Boolean,
    val isMine: Boolean
)

data class MessageImage(
    val imageId: Long,
    val imageUrl: String
)

data class TradeImage(
    val imageId: Long,
    val imageUrl: String
)

data class TradeProduct(
    val id: Long,
    val title: String,
    val images: PersistentList<TradeImage>?,
    val createdAt: String?,
    val isSeller: Boolean,
    val isCompletable: Boolean,
    val isCompleted: Boolean

)