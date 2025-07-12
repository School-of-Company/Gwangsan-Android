package com.school_of_company.profile.viewmodel.uistate

import com.school_of_company.model.post.response.Post

sealed interface GetMySpecificInformationUiState {
    data object Loading : GetMySpecificInformationUiState
    data object Empty : GetMySpecificInformationUiState
    data class Success(val data: Post) : GetMySpecificInformationUiState
    data class Error(val exception: Throwable) : GetMySpecificInformationUiState
}