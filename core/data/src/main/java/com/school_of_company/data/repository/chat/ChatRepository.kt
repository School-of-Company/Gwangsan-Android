package com.school_of_company.data.repository.chat

import com.school_of_company.model.chat.request.ReadMessageRequestModel
import com.school_of_company.model.chat.response.GetChatRoomResponseModel
import com.school_of_company.model.chat.response.JoinChatResponseModel
import com.school_of_company.network.socket.manager.ConnectionStatus
import com.school_of_company.network.socket.model.request.SendMessage
import com.school_of_company.network.socket.model.response.ChatMessage
import com.school_of_company.network.socket.model.response.RoomUpdate
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    fun createChatRoom(productId: Long) : Flow<JoinChatResponseModel>
    fun joinChatRoom(productId: Long) : Flow<JoinChatResponseModel>
    fun getChatRoomList() : Flow<List<GetChatRoomResponseModel>>
    fun getChatMessageList(
        roomId: Long,
        lastCreatedAt: String? = null,
        lastMessageId: Long? = null,
        limit: Int = 20
    ) : Flow<List<ChatMessage>>
    fun readChatMessage(body: ReadMessageRequestModel) : Flow<Unit>

    fun connectSocket(baseUrl: String, accessToken: String)
    fun sendMessage(message: SendMessage)
    fun disconnectSocket()

    val messageEvents: Flow<ChatMessage>
    val roomUpdateEvents: Flow<RoomUpdate>
    val connectionEvents: Flow<ConnectionStatus>
}