package com.school_of_company.profile.viewmodel.uistate

import com.school_of_company.model.member.request.ModifyMemberInformationRequestModel

sealed interface MyInForMatIonPeTchUiState {
    data object Loading : MyInForMatIonPeTchUiState
    data object Success: MyInForMatIonPeTchUiState
    data class Error(val exception: Throwable) : MyInForMatIonPeTchUiState
}