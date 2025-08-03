package com.school_of_company.chat.viewmodel.uistate

import com.school_of_company.model.chat.response.JoinChatResponseModel

sealed interface JoinChatUiState {
    data object Loading : JoinChatUiState
    data class Success(val data: JoinChatResponseModel) : JoinChatUiState
    data class Error(val exception: Throwable) : JoinChatUiState
}