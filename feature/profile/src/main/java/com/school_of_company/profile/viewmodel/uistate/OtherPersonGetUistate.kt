package com.school_of_company.profile.viewmodel.uistate

import com.school_of_company.model.member.response.GetAllMemberResponseModel
import com.school_of_company.model.member.response.GetMemberResponseModel

sealed interface OtherPersonGetUistate {
    data object Loading : OtherPersonGetUistate
    data object Empty : OtherPersonGetUistate
    data class Success(val data: GetAllMemberResponseModel) :
        OtherPersonGetUistate
    data class Error(val exception: Throwable) : OtherPersonGetUistate
}