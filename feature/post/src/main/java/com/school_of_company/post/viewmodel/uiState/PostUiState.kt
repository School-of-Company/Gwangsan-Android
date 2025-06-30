package com.school_of_company.post.viewmodel.uiState

sealed interface PostUiState {
    data object Loading : PostUiState
    data object Success : PostUiState
    data object BadRequest : PostUiState
    data object NotFound : PostUiState
    data class Error(val exception: Throwable) : PostUiState
}
