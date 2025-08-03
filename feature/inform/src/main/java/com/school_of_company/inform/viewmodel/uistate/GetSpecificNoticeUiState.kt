package com.school_of_company.inform.viewmodel.uistate

import com.school_of_company.model.notice.response.GetSpecificNoticeResponseModel

sealed interface GetSpecificNoticeUiState {
    data object Loading : GetSpecificNoticeUiState
    data class Success(val data: GetSpecificNoticeResponseModel) : GetSpecificNoticeUiState
    data class Error(val exception: Throwable) :GetSpecificNoticeUiState
}