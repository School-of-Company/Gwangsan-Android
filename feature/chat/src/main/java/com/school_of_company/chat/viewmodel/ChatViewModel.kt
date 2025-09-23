package com.school_of_company.chat.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.school_of_company.chat.BuildConfig
import com.school_of_company.chat.ui.mapper.toUi
import com.school_of_company.chat.ui.model.GetChatResponseUi
import com.school_of_company.chat.util.getMultipartFile
import com.school_of_company.chat.viewmodel.uistate.ChatMessageUiState
import com.school_of_company.chat.viewmodel.uistate.ChatTransactionCompleteUiState
import com.school_of_company.chat.viewmodel.uistate.DelteTradeReservationUiState
import com.school_of_company.chat.viewmodel.uistate.GetChatRoomUiState
import com.school_of_company.chat.viewmodel.uistate.GetLoadTradUiState
import com.school_of_company.chat.viewmodel.uistate.GetMySpecificInformationUiState
import com.school_of_company.chat.viewmodel.uistate.ImageUpLoadUiState
import com.school_of_company.chat.viewmodel.uistate.JoinChatUiState
import com.school_of_company.chat.viewmodel.uistate.TradeReservationUiState

import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.data.repository.chat.ChatRepository
import com.school_of_company.data.repository.image.ImageRepository
import com.school_of_company.data.repository.post.PostRepository
import com.school_of_company.model.post.request.TransactionCompleteRequestModel
import com.school_of_company.network.errorHandling
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
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository,
    private val imageRepository: ImageRepository,
    private val postRepository: PostRepository,

) : ViewModel() {



    private val _swipeRefreshLoading = MutableStateFlow(false)
    val swipeRefreshLoading = _swipeRefreshLoading.asStateFlow()

    private val _getLoadTradUiState = MutableStateFlow<GetLoadTradUiState>(GetLoadTradUiState.Loading)
    val getLoadTradUiState = _getLoadTradUiState.asStateFlow()

    private val _joinChatUiState = MutableStateFlow<JoinChatUiState>(JoinChatUiState.Loading)
    val joinChatUiState = _joinChatUiState.asStateFlow()

    private val _chatMessageUiState = MutableStateFlow<ChatMessageUiState>(ChatMessageUiState.Loading)
    val chatMessageUiState = _chatMessageUiState.asStateFlow()

    private val _transactionCompleteUiState = MutableStateFlow<ChatTransactionCompleteUiState>(ChatTransactionCompleteUiState.Loading)
    val transactionCompleteUiState = _transactionCompleteUiState.asStateFlow()

    private val _tradeReservationUiState = MutableStateFlow<TradeReservationUiState>(TradeReservationUiState.Loading)
    val tradeReservationUiState = _tradeReservationUiState.asStateFlow()

    private val _deleteTradeReservationUiState = MutableStateFlow<DelteTradeReservationUiState>(DelteTradeReservationUiState.Loading)
    val deleteTradeReservationUiState = _deleteTradeReservationUiState.asStateFlow()

    private val _getMySpecificInformationUiState = MutableStateFlow<GetMySpecificInformationUiState>(GetMySpecificInformationUiState.Loading)
    internal val getMySpecificInformationUiState = _getMySpecificInformationUiState.asStateFlow()

    private val _connectionStatus = MutableStateFlow(ConnectionStatus.DISCONNECTED)
    val connectionStatus = _connectionStatus.asStateFlow()

    private val _imageUpLoadUiState = MutableStateFlow<ImageUpLoadUiState>(ImageUpLoadUiState.Loading)
    internal val imageUpLoadUiState = _imageUpLoadUiState.asStateFlow()

    private val _getChatRoomUiState = MutableStateFlow<GetChatRoomUiState>(GetChatRoomUiState.Loading)
    internal val getChatRoomUiState = _getChatRoomUiState.asStateFlow()

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
                            val newMessageUi = newMessage.toUi()
                            val messageExists = currentMessages.any { it.messageId == newMessageUi.messageId }

                            if (messageExists) {
                                currentState
                            } else {
                                val updatedMessages = (currentMessages + newMessageUi).sortedBy { it.createdAt }
                                ChatMessageUiState.Success(updatedMessages.toPersistentList())
                            }
                        }
                        else -> {
                            ChatMessageUiState.Success(listOf(newMessage.toUi()).toPersistentList())
                        }
                    }
                }
            }
        }
    }

    internal fun transactionComplete(body: TransactionCompleteRequestModel) = viewModelScope.launch {
        _transactionCompleteUiState.value = ChatTransactionCompleteUiState.Loading
        postRepository.transactionComplete(body = body)
            .asResult()
            .collectLatest { result ->
                when (result){
                    is Result.Loading -> _transactionCompleteUiState.value = ChatTransactionCompleteUiState.Loading
                    is Result.Success -> _transactionCompleteUiState.value = ChatTransactionCompleteUiState.Success
                    is Result.Error -> {_transactionCompleteUiState.value = ChatTransactionCompleteUiState.Error(result.exception)
                    result.exception.errorHandling(
                        notFoundAction = {
                            _transactionCompleteUiState.value = ChatTransactionCompleteUiState.NotFound
                        },
                        unauthorizedAction = {
                            _transactionCompleteUiState.value = ChatTransactionCompleteUiState.Unauthorized
                        },
                        conflictAction = {
                            _transactionCompleteUiState.value = ChatTransactionCompleteUiState.Conflict
                        }
                    )
                    }

                }
            }
    }

    internal fun getChatRoom() = viewModelScope.launch {
        _swipeRefreshLoading.value = true
        _getChatRoomUiState.value = GetChatRoomUiState.Loading

        chatRepository.getChatRoomList()
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getChatRoomUiState.value = GetChatRoomUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            _getChatRoomUiState.value = GetChatRoomUiState.Empty
                            _swipeRefreshLoading.value = false
                        } else {
                            _getChatRoomUiState.value = GetChatRoomUiState.Success(result.data.map { it.toUi() }.toPersistentList())
                            _swipeRefreshLoading.value = false
                        }
                    }
                    is Result.Error -> {
                        _getChatRoomUiState.value = GetChatRoomUiState.Error(result.exception)
                        _swipeRefreshLoading.value = false
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

    suspend fun joinDirectChatRoom(roomId: Long) {
        if (!isSocketConnected) {
            connectSocket()
        }

        loadChatMessages(roomId)
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

    fun loadChatMessages(roomId: Long) = viewModelScope.launch {
        _getLoadTradUiState.value = GetLoadTradUiState.Loading

        chatRepository.getChatMessageList(
            roomId = roomId,
            lastCreatedAt = null,
            lastMessageId = null,
            limit = 50
        ).asResult().collectLatest { result ->
            when (result) {
                is Result.Loading -> {
                    _getLoadTradUiState.value = GetLoadTradUiState.Loading
                }

                is Result.Success -> {
                    val dto = result.data
                    val sortedUiMessages = dto.message
                        .sortedBy { it.createdAt }
                        .map { it.toUi() }

                    val ui = GetChatResponseUi(
                        message = sortedUiMessages,
                        product = dto.product.toUi()
                    )

                    _getLoadTradUiState.value = GetLoadTradUiState.Success(ui)
                }

                is Result.Error -> {
                    _getLoadTradUiState.value = GetLoadTradUiState.Error(result.exception)
                }
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
                baseUrl = "${BuildConfig.BASE_URL}api/chat",
                accessToken = token
            )
        }
    }

    fun disconnect() {
        chatRepository.disconnectSocket()
    }

    override fun onCleared() {
        super.onCleared()
        chatRepository.disconnectSocket()
    }

    internal fun getMyPostDetail(postId: Long) = viewModelScope.launch {
        postRepository.getSpecificInformation(postId = postId)
            .asResult()
            .collectLatest { result ->
                when (result) {
                    is Result.Loading -> _getMySpecificInformationUiState.value =
                        GetMySpecificInformationUiState.Loading

                    is Result.Success -> _getMySpecificInformationUiState.value =
                        GetMySpecificInformationUiState.Success(result.data)

                    is Result.Error -> _getMySpecificInformationUiState.value =
                        GetMySpecificInformationUiState.Error(result.exception)
                }
            }
    }

    fun tradeReservation(postId: Long){
        _tradeReservationUiState.value = TradeReservationUiState.Loading
        viewModelScope.launch {
            postRepository.transactionReservation(postId = postId)
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> _tradeReservationUiState.value =
                            TradeReservationUiState.Loading

                        is Result.Success -> _tradeReservationUiState.value =
                            TradeReservationUiState.Success

                        is Result.Error -> {
                            _tradeReservationUiState.value =
                                TradeReservationUiState.Error(result.exception)
                            result.exception.errorHandling(
                                notFoundAction = {
                                    _tradeReservationUiState.value = TradeReservationUiState.NotFound
                                },
                                badRequestAction = {
                                    _tradeReservationUiState.value = TradeReservationUiState.BadRequest
                                },
                                unauthorizedAction = {
                                    _tradeReservationUiState.value = TradeReservationUiState.Unauthorized
                                },
                                conflictAction = {
                                    _tradeReservationUiState.value = TradeReservationUiState.Conflict
                                }
                            )
                        }
                    }
                }
        }
    }

}