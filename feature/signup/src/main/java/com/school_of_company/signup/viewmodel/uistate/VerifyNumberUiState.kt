package com.school_of_company.signup.viewmodel.uistate

sealed interface VerifyNumberUiState {
    object Loading : VerifyNumberUiState
    object Success : VerifyNumberUiState
    object BadRequest : VerifyNumberUiState
    object Unauthorized : VerifyNumberUiState
    object Forbidden : VerifyNumberUiState
    data class Error(val exception: Throwable) : VerifyNumberUiState
}