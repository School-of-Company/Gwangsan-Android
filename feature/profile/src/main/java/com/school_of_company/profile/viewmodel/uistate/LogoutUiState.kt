package com.school_of_company.profile.viewmodel.uistate

interface LogoutUiState {
    data object Loading : LogoutUiState
    data object Success : LogoutUiState
    data class Error(val exception: Throwable) : LogoutUiState
}