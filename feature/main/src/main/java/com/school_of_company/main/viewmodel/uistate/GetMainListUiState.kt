package com.school_of_company.main.viewmodel.uistate

import com.school_of_company.model.post.response.Post

sealed interface GetMainListUiState {
    data object Loading : GetMainListUiState
    data object Empty : GetMainListUiState
    data class Success(val getMainListResponse : List<Post>) : GetMainListUiState
    data class Error(val exception: Throwable) : GetMainListUiState
}