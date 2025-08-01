package com.school_of_company.signup.viewmodel.uistate


sealed interface SignUpUiState {
    data object Loading : SignUpUiState
    data object Success : SignUpUiState
    data object Conflict : SignUpUiState
    data object Unauthorized: SignUpUiState
    data object PasswordMismatch : SignUpUiState
    data object PasswordNotValid : SignUpUiState
    data class Error(val exception: Throwable) : SignUpUiState
}