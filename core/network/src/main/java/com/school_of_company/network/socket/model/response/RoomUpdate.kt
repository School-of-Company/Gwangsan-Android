package com.school_of_company.network.socket.model.response

data class RoomUpdate(
    val roomId: Long,
    val lastMessage: String,
    val lastMessageType: String,
    val lastMessageTime: String
)