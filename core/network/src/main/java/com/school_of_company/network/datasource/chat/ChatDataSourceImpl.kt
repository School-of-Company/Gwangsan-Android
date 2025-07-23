package com.school_of_company.network.datasource.chat

import com.school_of_company.network.api.ChatAPI
import com.school_of_company.network.dto.chat.request.ReadMessageRequest
import com.school_of_company.network.dto.chat.response.GetChatMessageResponse
import com.school_of_company.network.dto.chat.response.GetChatRoomResponse
import com.school_of_company.network.dto.chat.response.JoinChatResponse
import com.school_of_company.network.socket.dto.request.SendMessageDto
import com.school_of_company.network.socket.dto.response.ChatMessageDto
import com.school_of_company.network.socket.manager.ConnectionStatus
import com.school_of_company.network.socket.manager.SocketManager
import com.school_of_company.network.socket.model.response.ChatMessage
import com.school_of_company.network.socket.model.response.RoomUpdate
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatDataSourceImpl @Inject constructor(
    private val chatAPI: ChatAPI,
    private val socketManager: SocketManager
) : ChatDataSource {
    override fun createChatRoom(productId: Long): Flow<JoinChatResponse> =
        performApiRequest { chatAPI.createChatRoom(productId = productId) }

    override fun joinChatRoom(productId: Long): Flow<JoinChatResponse> =
        performApiRequest { chatAPI.joinChatRoom(productId = productId) }

    override fun getChatRoomList(): Flow<List<GetChatRoomResponse>> =
        performApiRequest { chatAPI.getChatRoomList() }

    override fun getChatMessageList(
        roomId: Long,
        lastCreatedAt: String?,
        lastMessageId: Long?,
        limit: Int
    ): Flow<List<ChatMessageDto>> =
        performApiRequest { chatAPI.getChatMessageList(
            roomId = roomId,
            lastCreatedAt = lastCreatedAt,
            lastMessageId = lastMessageId,
            limit = limit
        ) }

    override fun readChatMessage(body: ReadMessageRequest): Flow<Unit> =
        performApiRequest { chatAPI.readChatMessage(body = body) }

    override fun connectSocket(baseUrl: String, accessToken: String) {
        socketManager.connectChatting(baseUrl = baseUrl, accessToken = accessToken)
    }

    override fun sendMessage(message: SendMessageDto) {
        socketManager.sendMessage(message)
    }

    override fun disconnectSocket() {
        socketManager.disconnect()
    }

    override val messageEvents: Flow<ChatMessage> = socketManager.messageEvents
    override val roomUpdateEvents: Flow<RoomUpdate> = socketManager.roomUpdateEvents
    override val connectionEvents: Flow<ConnectionStatus> = socketManager.connectionEvents
}