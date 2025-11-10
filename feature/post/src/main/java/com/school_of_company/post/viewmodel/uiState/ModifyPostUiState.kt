package com.school_of_company.post.viewmodel.uiState

interface ModifyPostUiState {
    data object Loading : ModifyPostUiState
    data object Success : ModifyPostUiState
    data object NotFoundImage : ModifyPostUiState

    data class Error(val exception: Throwable) : ModifyPostUiState
}