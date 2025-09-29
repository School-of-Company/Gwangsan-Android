package com.school_of_company.content.viewmodel.uistate

import androidx.compose.runtime.Stable

interface TransactionCompleteUiState {
    @Stable
    data object Loading : TransactionCompleteUiState

    @Stable
    data object Success : TransactionCompleteUiState

    @Stable
    data object Unauthorized : TransactionCompleteUiState

    @Stable
    data object  NotFound : TransactionCompleteUiState

    @Stable
    data object  Conflict : TransactionCompleteUiState

    @Stable
    data class Error(val exception: Throwable) : TransactionCompleteUiState
}