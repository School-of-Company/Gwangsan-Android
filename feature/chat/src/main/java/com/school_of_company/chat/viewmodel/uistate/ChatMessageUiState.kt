package com.school_of_company.chat.viewmodel.uistate

import com.school_of_company.model.chat.response.GetChatMessageResponseModel
import com.school_of_company.network.socket.model.response.ChatMessage

sealed interface ChatMessageUiState {
    data object Loading : ChatMessageUiState
    data class Success(val data: List<ChatMessage>) : ChatMessageUiState
    data class Error(val exception: Throwable) : ChatMessageUiState
}