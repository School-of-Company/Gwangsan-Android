package com.school_of_company.network.dto.member.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModifyMemberInformationRequest(
    @Json(name = "nickname") val nickname: String,
    @Json(name = "profileUrl") val profileUrl: String
)
