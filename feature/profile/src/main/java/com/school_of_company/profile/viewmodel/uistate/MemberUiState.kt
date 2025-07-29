package com.school_of_company.profile.viewmodel.uistate

import androidx.compose.runtime.Stable
import com.school_of_company.profile.ui.model.GetMemberResponseUi

@Stable
sealed interface MemberUiState {

    @Stable
    data object Loading : MemberUiState

    @Stable
    data object Empty : MemberUiState

    @Stable
    data class Success(val data: GetMemberResponseUi) : MemberUiState

    @Stable
    data class Error(val exception: Throwable) : MemberUiState
}