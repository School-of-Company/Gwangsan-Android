package com.school_of_company.content.viewmodel.uistate

interface ReportPostUiState {
    data object Loading : ReportPostUiState
    data object Success : ReportPostUiState
    data class Error(val exception: Throwable) : ReportPostUiState
}