package com.school_of_company.network.socket.mapper.response

import com.school_of_company.network.socket.dto.response.RoomUpdateDto
import com.school_of_company.network.socket.model.response.RoomUpdate

fun RoomUpdateDto.toModel(): RoomUpdate = RoomUpdate(
    roomId = this.roomId,
    lastMessage = this.lastMessage,
    lastMessageType = this.lastMessageType,
    lastMessageTime = this.lastMessageTime
)