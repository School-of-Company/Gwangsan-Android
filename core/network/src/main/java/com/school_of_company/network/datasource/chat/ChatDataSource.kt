package com.school_of_company.network.datasource.chat

import com.school_of_company.network.dto.chat.request.ReadMessageRequest
import com.school_of_company.network.dto.chat.response.GetChatMessageResponse
import com.school_of_company.network.dto.chat.response.GetChatRoomResponse
import com.school_of_company.network.dto.chat.response.JoinChatResponse
import com.school_of_company.network.socket.dto.request.SendMessageDto
import com.school_of_company.network.socket.manager.ConnectionStatus
import com.school_of_company.network.socket.model.response.ChatMessage
import com.school_of_company.network.socket.model.response.RoomUpdate
import kotlinx.coroutines.flow.Flow

interface ChatDataSource {

    fun createChatRoom(productId: Long) : Flow<JoinChatResponse>
    fun joinChatRoom(productId: Long) : Flow<JoinChatResponse>
    fun getChatRoomList() : Flow<List<GetChatRoomResponse>>
    fun getChatMessageList(
        roomId: Long,
        lastCreatedAt: String? = null,
        lastMessageId: Long? = null,
        limit: Int = 20
    ) : Flow<List<GetChatMessageResponse>>
    fun readChatMessage(body: ReadMessageRequest) : Flow<Unit>

    fun connectSocket(baseUrl: String, accessToken: String)
    fun sendMessage(message: SendMessageDto)
    fun disconnectSocket()

    val messageEvents: Flow<ChatMessage>
    val roomUpdateEvents: Flow<RoomUpdate>
    val connectionEvents: Flow<ConnectionStatus>
}