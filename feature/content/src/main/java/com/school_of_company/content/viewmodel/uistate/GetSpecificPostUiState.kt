package com.school_of_company.content.viewmodel.uistate

import com.school_of_company.model.post.response.Post

interface GetSpecificPostUiState {
    data object Loading : GetSpecificPostUiState
    data class Success(val post: Post) : GetSpecificPostUiState
    data class Error(val exception: Throwable) : GetSpecificPostUiState
}