package com.school_of_company.profile.viewmodel.uistate

import com.school_of_company.model.post.response.AllPost
import com.school_of_company.model.post.response.Post

sealed interface OtherGetPostUiState{
    data object Loading : OtherGetPostUiState
    data object Empty : OtherGetPostUiState
    data class Success(val data: List<AllPost>) : OtherGetPostUiState
    data class Error(val exception: Throwable) : OtherGetPostUiState
}