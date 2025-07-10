package com.school_of_company.profile.viewmodel.uistate

import com.school_of_company.model.member.response.GetMemberResponseModel

sealed interface MemberUiState {
    data object Loading : MemberUiState
    data object Empty : MemberUiState
    data class Success(val data: GetMemberResponseModel) : MemberUiState
    data class Error(val exception: Throwable) : MemberUiState
}