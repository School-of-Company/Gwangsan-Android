package com.school_of_company.model.member.request

data class ModifyMemberInformationRequestModel(
    val nickname: String,
    val specialties:List<String>,
    val description: String
)
