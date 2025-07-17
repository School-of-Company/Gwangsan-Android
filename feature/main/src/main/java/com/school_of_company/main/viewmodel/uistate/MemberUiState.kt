package com.school_of_company.main.viewmodel.uistate

import com.school_of_company.model.member.response.GetMemberResponseModel

interface MemberUiState {
    data object Loading : MemberUiState
    data class Success(val data: GetMemberResponseModel) : MemberUiState
    data class Error(val exception: Throwable) : MemberUiState
}