package com.school_of_company.main.viewmodel.uistate

import com.school_of_company.model.alert.response.GetAlertResponseModel
import com.school_of_company.model.alert.response.GetReadAlertModel

sealed interface GetUnReadAlertUiState {
    data object Loading : GetUnReadAlertUiState
    data class Success(val data : GetReadAlertModel) : GetUnReadAlertUiState
    data class Error(val exception: Throwable) : GetUnReadAlertUiState
}