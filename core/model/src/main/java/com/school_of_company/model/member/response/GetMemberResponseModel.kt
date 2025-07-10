package com.school_of_company.model.member.response

data class GetMemberResponseModel(
    val memberId: Long,
    val nickname: String,
    val placeName: String,
    val light: Int,
    val gwangsan: Int,
    val description: String,
    val specialties: List<String>
)
