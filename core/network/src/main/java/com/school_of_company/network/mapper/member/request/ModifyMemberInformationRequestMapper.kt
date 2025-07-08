package com.school_of_company.network.mapper.member.request

import com.school_of_company.model.member.request.ModifyMemberInformationRequestModel
import com.school_of_company.network.dto.member.request.ModifyMemberInformationRequest

fun ModifyMemberInformationRequestModel.toDto(): ModifyMemberInformationRequest =
    ModifyMemberInformationRequest(
        nickname = this.nickname,
        profileUrl = this.profileUrl
    )