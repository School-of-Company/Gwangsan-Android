package com.school_of_company.main.viewmodel.uistate

interface TransactionCompleteUiState {
    data object Loading : TransactionCompleteUiState
    data object Success : TransactionCompleteUiState
    data class Error(val exception: Throwable) : TransactionCompleteUiState
}