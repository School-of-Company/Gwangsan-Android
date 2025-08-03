package com.school_of_company.content.viewmodel.uistate

interface TransactionCompleteUiState {
    data object Loading : TransactionCompleteUiState
    data object Success : TransactionCompleteUiState
    data class Error(val exception: Throwable) : TransactionCompleteUiState
}