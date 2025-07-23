package com.school_of_company.model.chat.response

data class GetChatRoomResponseModel(
    val roomId: Long,
    val member: GetMemberResponseModel,
    val messageId: Long,
    val lastMessage: String,
    val lastMessageType: String,
    val lastMessageTime: String,
    val unreadMessageCount: Long,
    val product: GetProductResponseModel
)

data class GetProductResponseModel(
    val productId: Long,
    val title: String,
    val images: List<GetImageResponseModel>?
)

data class GetImageResponseModel(
    val imageId: Long,
    val imageUrl: String
)

data class GetMemberResponseModel(
    val memberId: Long,
    val nickname: String
)