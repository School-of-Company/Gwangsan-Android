package com.school_of_company.content.viewmodel.uistate

sealed interface ReviewPostUiState {
    object Loading : ReviewPostUiState
    object Success : ReviewPostUiState
    data class Error(val exception: Throwable) : ReviewPostUiState
}
