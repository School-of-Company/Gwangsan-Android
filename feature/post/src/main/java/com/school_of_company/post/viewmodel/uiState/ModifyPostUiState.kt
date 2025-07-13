package com.school_of_company.post.viewmodel.uiState

interface ModifyPostUiState {
    data object Loading : ModifyPostUiState
    data object Success : ModifyPostUiState
    data class Error(val exception: Throwable) : ModifyPostUiState
}