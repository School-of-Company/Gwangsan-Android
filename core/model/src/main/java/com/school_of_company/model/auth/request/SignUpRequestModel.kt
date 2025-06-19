package com.school_of_company.model.auth.request

import com.school_of_company.model.enum.Branch
import com.school_of_company.model.enum.Dong
import com.school_of_company.model.enum.Specialty

data class SignUpRequestModel(
    val name: String,
    val nickname: String, // 한글만 허용할 경우 유효성 검사 필요
    val password: String,
    val phoneNumber: String,
    val verificationCode: String,
    val dong: Dong,
    val branch: Branch,
    val recommender: String,
    val introduction: String,
    val specialty: Specialty
)