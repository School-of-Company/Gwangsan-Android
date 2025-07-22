package com.school_of_company.chat.viewmodel.uistate

import com.school_of_company.model.chat.response.GetChatRoomResponseModel

sealed interface GetChatRoomUiState {
    data object Loading : GetChatRoomUiState
    data object Empty : GetChatRoomUiState
    data class Success(val data: List<GetChatRoomResponseModel>) : GetChatRoomUiState
    data class Error(val exception: Throwable) : GetChatRoomUiState
}