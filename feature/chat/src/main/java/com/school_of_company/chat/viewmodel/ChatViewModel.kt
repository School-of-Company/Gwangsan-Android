package com.school_of_company.chat.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.chat.util.getMultipartFile
import com.school_of_company.chat.viewmodel.uistate.ChatMessageUiState
import com.school_of_company.chat.viewmodel.uistate.ImageUpLoadUiState
import com.school_of_company.chat.viewmodel.uistate.JoinChatUiState
import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.data.repository.chat.ChatRepository
import com.school_of_company.data.repository.image.ImageRepository
import com.school_of_company.network.socket.manager.ConnectionStatus
import com.school_of_company.network.socket.model.request.SendMessage
import com.school_of_company.result.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.school_of_company.result.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _joinChatUiState = MutableStateFlow<JoinChatUiState>(JoinChatUiState.Loading)
    val joinChatUiState = _joinChatUiState.asStateFlow()

    private val _chatMessageUiState = MutableStateFlow<ChatMessageUiState>(ChatMessageUiState.Loading)
    val chatMessageUiState = _chatMessageUiState.asStateFlow()

    private val _connectionStatus = MutableStateFlow<ConnectionStatus>(ConnectionStatus.DISCONNECTED)
    val connectionStatus = _connectionStatus.asStateFlow()

    private val _imageUpLoadUiState = MutableStateFlow<ImageUpLoadUiState>(ImageUpLoadUiState.Loading)
    internal val imageUpLoadUiState = _imageUpLoadUiState.asStateFlow()

    private var isSocketConnected = false

    init {
        viewModelScope.launch {
            chatRepository.connectionEvents.collectLatest { status ->
                _connectionStatus.value = status
                isSocketConnected = status == ConnectionStatus.CONNECTED
            }
        }

        viewModelScope.launch {
            chatRepository.messageEvents.collectLatest { newMessage ->

                _chatMessageUiState.update { currentState ->
                    when (currentState) {
                        is ChatMessageUiState.Success -> {
                            val currentMessages = currentState.data
                            val messageExists = currentMessages.any { it.messageId == newMessage.messageId }

                            if (messageExists) {
                                currentState
                            } else {
                                val updatedMessages = (currentMessages + newMessage).sortedBy { it.createdAt }
                                ChatMessageUiState.Success(updatedMessages)
                            }
                        }
                        else -> {
                            ChatMessageUiState.Success(listOf(newMessage))
                        }
                    }
                }
            }
        }
    }

    internal suspend fun imageUpLoad(context: Context, image: Uri): Long {
        val multipartFile = getMultipartFile(context, image)
            ?: throw IllegalStateException("이미지 파일 변환 실패")

        return try {
            imageRepository.imageUpLoad(multipartFile)
                .asResult()
                .first { result ->
                    result !is Result.Loading
                }.let { result ->
                    when (result) {
                        is Result.Success -> {
                            _imageUpLoadUiState.value = ImageUpLoadUiState.Success(result.data)
                            result.data.imageId
                        }
                        is Result.Error -> {
                            _imageUpLoadUiState.value = ImageUpLoadUiState.Error(result.exception)
                            throw result.exception
                        }
                        is Result.Loading -> {
                            throw IllegalStateException("Unexpected loading state")
                        }
                    }
                }
        } catch (e: Exception) {
            _imageUpLoadUiState.value = ImageUpLoadUiState.Error(e)
            throw e
        }
    }

    fun joinOrCreateChatRoom(productId: Long) = viewModelScope.launch {
        _joinChatUiState.value = JoinChatUiState.Loading

        if (!isSocketConnected) {
            connectSocket()
        }

        chatRepository.joinChatRoom(productId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _joinChatUiState.value = JoinChatUiState.Loading
                    is Result.Success -> {
                        _joinChatUiState.value = JoinChatUiState.Success(result.data)

                        chatRepository.emitJoinRoom(result.data.roomId)

                        loadChatMessages(result.data.roomId)
                    }
                    is Result.Error -> {
                        createChatRoom(productId)
                    }
                }
            }
    }

    private fun createChatRoom(productId: Long) = viewModelScope.launch {
        chatRepository.createChatRoom(productId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _joinChatUiState.value = JoinChatUiState.Loading
                    is Result.Success -> {
                        _joinChatUiState.value = JoinChatUiState.Success(result.data)
                        loadChatMessages(result.data.roomId)

                        if (!isSocketConnected) {
                            connectSocket()
                        }
                    }
                    is Result.Error -> _joinChatUiState.value = JoinChatUiState.Error(result.exception)
                }
            }
    }

    private fun loadChatMessages(roomId: Long) = viewModelScope.launch {
        _chatMessageUiState.value = ChatMessageUiState.Loading

        chatRepository.getChatMessageList(
            roomId = roomId,
            lastCreatedAt = null,
            lastMessageId = null,
            limit = 50
        ).asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _chatMessageUiState.value = ChatMessageUiState.Loading
                    is Result.Success -> {
                        val sortedMessages = result.data.sortedBy { it.createdAt }
                        _chatMessageUiState.value = ChatMessageUiState.Success(sortedMessages)
                    }
                    is Result.Error -> _chatMessageUiState.value = ChatMessageUiState.Error(result.exception)
                }
            }
    }

    fun sendMessage(roomId: Long, content: String, imageIds: List<Long> = emptyList()) {
        if (!isSocketConnected) return

        val messageType = if (imageIds.isNotEmpty()) "IMAGE" else "TEXT"

        val message = SendMessage(
            roomId = roomId,
            content = content,
            messageType = messageType,
            imageIds = imageIds
        )
        chatRepository.sendMessage(message)
    }

    private suspend fun connectSocket() {
        val token = authRepository.getAccessToken().first()
        if (token.isNotBlank()) {
            chatRepository.connectSocket(
                baseUrl = "https://api.gwangsan.io.kr/api/chat",
                accessToken = token
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        chatRepository.disconnectSocket()
    }
}