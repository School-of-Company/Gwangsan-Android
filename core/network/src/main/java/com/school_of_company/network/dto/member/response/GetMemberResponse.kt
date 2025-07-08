package com.school_of_company.network.dto.member.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetMemberResponse(
    @Json(name = "memberId") val memberId: Long,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "placeName") val placeName: String,
    @Json(name = "profileUrl") val profileUrl: String,
    @Json(name = "light") val light: Int,
    @Json(name = "gwangsan") val gwangsan: Int
)
