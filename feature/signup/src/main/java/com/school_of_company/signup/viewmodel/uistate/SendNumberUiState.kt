package com.school_of_company.signup.viewmodel.uistate

sealed interface SendNumberUiState {
    data object Loading : SendNumberUiState
    data object Success : SendNumberUiState
    data object PhoneNumberNotValid : SendNumberUiState
    data object TooManyRequest : SendNumberUiState
    data class Error(val exception: Throwable) : SendNumberUiState
}