package com.school_of_company.data.repository.chat

import com.school_of_company.model.chat.request.ReadMessageRequestModel
import com.school_of_company.model.chat.response.GetChatRoomResponseModel
import com.school_of_company.model.chat.response.JoinChatResponseModel
import com.school_of_company.network.datasource.chat.ChatDataSource
import com.school_of_company.network.mapper.chat.request.toDto
import com.school_of_company.network.mapper.chat.response.toModel
import com.school_of_company.network.socket.manager.ConnectionStatus
import com.school_of_company.network.socket.mapper.request.toDto
import com.school_of_company.network.socket.mapper.response.toModel
import com.school_of_company.network.socket.model.request.SendMessage
import com.school_of_company.network.socket.model.response.ChatMessage
import com.school_of_company.network.socket.model.response.GetChatResponse
import com.school_of_company.network.socket.model.response.RoomUpdate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDataSource: ChatDataSource
) : ChatRepository {
    override fun createChatRoom(productId: Long): Flow<JoinChatResponseModel> {
        return chatDataSource.createChatRoom(productId = productId).map { it.toModel() }
    }

    override fun joinChatRoom(productId: Long): Flow<JoinChatResponseModel> {
        return chatDataSource.joinChatRoom(productId = productId).map { it.toModel() }
    }

    override fun getChatRoomList(): Flow<List<GetChatRoomResponseModel>> {
        return chatDataSource.getChatRoomList().map { it.map { list -> list.toModel() } }
    }

    override fun getChatMessageList(
        roomId: Long,
        lastCreatedAt: String?,
        lastMessageId: Long?,
        limit: Int
    ): Flow<GetChatResponse> {
        return chatDataSource.getChatMessageList(
            roomId = roomId,
            lastCreatedAt = lastCreatedAt,
            lastMessageId = lastMessageId,
            limit = limit
        ).map { it.toModel() }
    }

    override fun readChatMessage(body: ReadMessageRequestModel): Flow<Unit> {
        return chatDataSource.readChatMessage(body = body.toDto())
    }

    override fun connectSocket(baseUrl: String, accessToken: String) {
        return chatDataSource.connectSocket(baseUrl = baseUrl, accessToken = accessToken)
    }

    override fun sendMessage(message: SendMessage) {
        return chatDataSource.sendMessage(message = message.toDto())
    }

    override fun disconnectSocket() {
        return chatDataSource.disconnectSocket()
    }

    override val messageEvents: Flow<ChatMessage> = chatDataSource.messageEvents
    override val roomUpdateEvents: Flow<RoomUpdate> = chatDataSource.roomUpdateEvents
    override val connectionEvents: Flow<ConnectionStatus> = chatDataSource.connectionEvents
}