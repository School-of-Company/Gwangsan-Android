package com.school_of_company.signin.viewmodel.uistate

sealed interface SignInUiState {
    object Loading : SignInUiState
    object Success : SignInUiState
    object NotFound : SignInUiState
    object IdNotValid : SignInUiState
    data class Error(val exception: Throwable) : SignInUiState
}