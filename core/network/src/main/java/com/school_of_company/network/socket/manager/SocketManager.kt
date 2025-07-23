package com.school_of_company.network.socket.manager

import android.util.Log
import com.school_of_company.network.socket.dto.request.SendMessageDto
import com.school_of_company.network.socket.dto.response.ChatMessageDto
import com.school_of_company.network.socket.dto.response.RoomUpdateDto
import com.school_of_company.network.socket.mapper.response.toModel
import com.school_of_company.network.socket.model.response.ChatMessage
import com.school_of_company.network.socket.model.response.RoomUpdate
import com.squareup.moshi.Moshi
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.json.JSONObject
import javax.inject.Inject

enum class ConnectionStatus {
    CONNECTING, CONNECTED, DISCONNECTED, ERROR
}

class SocketManager @Inject constructor(
    moshi: Moshi
) {
    private var socket: Socket? = null


    private val messageAdapter = moshi.adapter(ChatMessageDto::class.java)
    private val roomUpdateAdapter = moshi.adapter(RoomUpdateDto::class.java)
    private val sendMessageAdapter = moshi.adapter(SendMessageDto::class.java)

    private val _messageEvents = Channel<ChatMessage>(Channel.UNLIMITED)
    val messageEvents: Flow<ChatMessage> = _messageEvents.receiveAsFlow()

    private val _roomUpdateEvents = Channel<RoomUpdate>(Channel.UNLIMITED)
    val roomUpdateEvents: Flow<RoomUpdate> = _roomUpdateEvents.receiveAsFlow()

    private val _connectionEvents = Channel<ConnectionStatus>(Channel.UNLIMITED)
    val connectionEvents: Flow<ConnectionStatus> = _connectionEvents.receiveAsFlow()

    fun connectChatting(baseUrl: String, accessToken: String) {
        try {
            disconnect()

            val options = IO.Options().apply {
                auth = mapOf("token" to "Bearer $accessToken")
                transports = arrayOf("websocket")
                forceNew = true
                reconnection = true
                timeout = 10000
            }

            socket = IO.socket(baseUrl, options)

            setupEventListeners()
            socket?.connect()

            _connectionEvents.trySend(ConnectionStatus.CONNECTING)
        } catch (e: Exception) {
            Log.e("SocketManager", "Exception during socket connection", e)
            _connectionEvents.trySend(ConnectionStatus.ERROR)
        }
    }

    private fun setupEventListeners() {
        socket?.apply {

            on(Socket.EVENT_CONNECT) {
                _connectionEvents.trySend(ConnectionStatus.CONNECTED)
            }

            on(Socket.EVENT_CONNECT_ERROR) {
                _connectionEvents.trySend(ConnectionStatus.ERROR)
            }

            on(Socket.EVENT_DISCONNECT) {
                _connectionEvents.trySend(ConnectionStatus.DISCONNECTED)
            }

            on("receiveMessage") { args ->
                try {
                    val jsonObject = (args.getOrNull(0) as? JSONObject) ?: return@on
                    val messageDto = messageAdapter.fromJson(jsonObject.toString())
                    messageDto?.let {
                        _messageEvents.trySend(it.toModel())
                    }

                } catch (e: Exception) {
                    Log.e("SocketManager", "Error parsing message", e)
                }
            }

            on("updateRoomList") { args ->

                try {
                    val jsonObject = (args.getOrNull(0) as? JSONObject) ?: return@on
                    val roomUpdateDto = roomUpdateAdapter.fromJson(jsonObject.toString())
                    roomUpdateDto?.let {
                        _roomUpdateEvents.trySend(it.toModel())
                    }

                } catch (e: Exception) {
                    Log.e("SocketManager", "Error parsing room update", e)
                }
            }
        }
    }

    fun sendMessage(message: SendMessageDto) {
        try {
            val jsonString = sendMessageAdapter.toJson(message)
            val jsonObject = JSONObject(jsonString)
            socket?.emit("sendMessage", jsonObject)
        } catch (e: Exception) {
            Log.e("SocketManager", "Error sending message", e)
        }
    }

    fun disconnect() {
        socket?.disconnect()
        socket?.off()
        socket = null
        _connectionEvents.trySend(ConnectionStatus.DISCONNECTED)
    }
}