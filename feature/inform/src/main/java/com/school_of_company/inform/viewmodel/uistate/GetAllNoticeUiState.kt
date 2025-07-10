package com.school_of_company.inform.viewmodel.uistate

import com.school_of_company.model.notice.response.GetAllNoticeResponseModel

sealed interface GetAllNoticeUiState {
    data object Loading : GetAllNoticeUiState
    data object Empty : GetAllNoticeUiState
    data class Success(val data: List<GetAllNoticeResponseModel>) : GetAllNoticeUiState
    data class Error(val exception: Throwable) : GetAllNoticeUiState
}