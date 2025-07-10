package com.school_of_company.profile.viewmodel.uistate

import com.school_of_company.model.member.response.GetAllMemberResponseModel
import com.school_of_company.model.post.response.Post

sealed interface GetMyPostUiState {
    data object Loading : GetMyPostUiState
    data object Empty : GetMyPostUiState
    data class Success(val data: List<Post>) :
        GetMyPostUiState
    data class Error(val exception: Throwable) : GetMyPostUiState
}