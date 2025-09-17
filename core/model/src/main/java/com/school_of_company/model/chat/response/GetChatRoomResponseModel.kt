package com.school_of_company.model.chat.response

import kotlinx.collections.immutable.PersistentList

data class GetChatRoomResponseModel(
    val roomId: Long,
    val member: GetMemberResponseModel,

    val messageId: Long?,            // ✅ nullable
    val lastMessage: String?,        // ✅ nullable
    val lastMessageType: String?,    // ✅ nullable
    val lastMessageTime: String?,    // ✅ nullable

    val unreadMessageCount: Long,    // 그대로 non-null
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
