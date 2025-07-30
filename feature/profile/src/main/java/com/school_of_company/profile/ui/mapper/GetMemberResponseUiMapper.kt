package com.school_of_company.profile.ui.mapper

import com.school_of_company.model.member.response.GetMemberResponseModel
import com.school_of_company.profile.ui.model.GetMemberResponseUi

/**
 * Socket 응답 → UI 모델 변환
 */

fun GetMemberResponseModel.toUi() : GetMemberResponseUi =
    GetMemberResponseUi(
        memberId = this.memberId,
        nickname = this.nickname,
        placeName = this.placeName,
        light = this.light,
        gwangsan = this.gwangsan,
        description = this.description,
        specialties = this.specialties
    )