package com.school_of_company.network.mapper.member.response

import com.school_of_company.model.member.response.GetMemberResponseModel
import com.school_of_company.network.dto.member.response.GetMemberResponse

fun GetMemberResponse.toModel(): GetMemberResponseModel =
    GetMemberResponseModel(
        memberId = this.memberId,
        nickname = this.nickname,
        placeName = this.placeName,
        profileUrl = this.profileUrl,
        light = this.light,
        gwangsan = this.gwangsan
    )