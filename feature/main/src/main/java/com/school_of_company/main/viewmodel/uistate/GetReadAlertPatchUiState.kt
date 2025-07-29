package com.school_of_company.main.viewmodel.uistate

import com.school_of_company.model.alert.response.GetReadAlertModel

sealed interface GetReadAlertPatchUiState {
    data object Loading : GetReadAlertPatchUiState
    data object Success : GetReadAlertPatchUiState
    data class Error(val exception: Throwable) : GetReadAlertPatchUiState
}