package com.school_of_company.chat.viewmodel.uistate

sealed interface ReviewChatUiState {
    object Loading : ReviewChatUiState
    object Success : ReviewChatUiState
    data class Error(val exception: Throwable) : ReviewChatUiState
}
