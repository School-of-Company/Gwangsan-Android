package com.school_of_company.main.viewmodel.uistate

import com.school_of_company.model.alert.response.GetAlertResponseModel

sealed interface GetAlertUiState {
    data object Loading : GetAlertUiState
    data object Empty : GetAlertUiState
    data class Success(val data : List<GetAlertResponseModel>) : GetAlertUiState
    data class Error(val exception: Throwable) : GetAlertUiState
}