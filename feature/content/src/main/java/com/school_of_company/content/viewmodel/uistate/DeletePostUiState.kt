package com.school_of_company.content.viewmodel.uistate

sealed interface DeletePostUiState {
    data object Loading : DeletePostUiState
    data object Success : DeletePostUiState
    data class Error(val exception: Throwable) : DeletePostUiState
}