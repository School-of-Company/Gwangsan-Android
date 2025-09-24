package com.school_of_company.chat.viewmodel.uistate

import androidx.compose.runtime.Stable


@Stable
sealed interface ChatTransactionCompleteUiState {
    @Stable
    data object Loading : ChatTransactionCompleteUiState

    @Stable
    data object Success : ChatTransactionCompleteUiState

    @Stable
    data object Unauthorized : ChatTransactionCompleteUiState

    @Stable
    data object  NotFound : ChatTransactionCompleteUiState

    @Stable
    data object  Conflict : ChatTransactionCompleteUiState

    @Stable
    data class Error(val exception: Throwable) : ChatTransactionCompleteUiState
}