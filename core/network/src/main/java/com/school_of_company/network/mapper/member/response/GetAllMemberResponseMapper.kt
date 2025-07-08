package com.school_of_company.network.mapper.member.response

import com.school_of_company.model.member.response.GetAllMemberResponseModel
import com.school_of_company.network.dto.member.response.GetAllMemberResponse

fun GetAllMemberResponse.toModel(): GetAllMemberResponseModel =
    GetAllMemberResponseModel(
        memberId = this.memberId,
        nickname = this.nickname,
        profileUrl = this.profileUrl,
        placeName = this.placeName,
        light = this.light
    )