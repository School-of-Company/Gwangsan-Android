package com.school_of_company.profile.ui.mapper

import com.school_of_company.model.member.response.GetAllMemberResponseModel
import com.school_of_company.profile.ui.model.GetAllMemberResponseUi

/**
 * Socket 응답 → UI 모델 변환
 */

fun GetAllMemberResponseModel.toUi() : GetAllMemberResponseUi =
    GetAllMemberResponseUi(
        memberId = this.memberId,
        nickname = this.nickname,
        placeName = this.placeName,
        light = this.light,
        description = this.description,
        specialties = this.specialties
    )