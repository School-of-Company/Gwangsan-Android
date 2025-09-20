package com.school_of_company.model.chat.response

import kotlinx.collections.immutable.PersistentList

data class GetChatRoomResponseModel(
    val roomId: Long,
    val member: GetMemberResponseModel,

    val messageId: Long?,
    val lastMessage: String?,
    val lastMessageType: String?,
    val lastMessageTime: String?,
    val unreadMessageCount: Long,
    val product: GetProductResponseModel
)

data class GetProductResponseModel(
    val productId: Long,
    val title: String,
    val images: PersistentList<GetImageResponseModel>? // 그대로 nullable
)

data class GetImageResponseModel(
    val imageId: Long,
    val imageUrl: String
)

data class GetMemberResponseModel(
    val memberId: Long,
    val nickname: String
)
