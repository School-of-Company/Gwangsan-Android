package com.school_of_company.signin.viewmodel.uistate

sealed interface SignInUiState {
    data object Loading : SignInUiState
    data object Success : SignInUiState
    data object NotFound : SignInUiState
    data object IdNotValid : SignInUiState
    data object BadRequest: SignInUiState
    data class Error(val exception: Throwable) : SignInUiState
}