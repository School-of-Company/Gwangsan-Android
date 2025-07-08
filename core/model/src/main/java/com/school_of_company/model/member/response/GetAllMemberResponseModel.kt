package com.school_of_company.model.member.response

data class GetAllMemberResponseModel(
    val memberId: Long,
    val nickname: String,
    val profileUrl: String,
    val placeName: String,
    val light: Int
)
