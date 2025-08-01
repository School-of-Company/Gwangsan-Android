package com.school_of_company.profile.viewmodel.uistate

sealed interface WithdrawalUiState {
    data object Loading : WithdrawalUiState
    data object Success : WithdrawalUiState
    data class Error(val exception: Throwable) : WithdrawalUiState
}