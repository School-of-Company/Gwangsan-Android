package com.school_of_company.model.auth.request

import com.school_of_company.model.enum.branch
import com.school_of_company.model.enum.dong
import com.school_of_company.model.enum.specialty

data class SignUpRequestModelBody(
    val name: String,
    val nickname: String, // 한글만 허용할 경우 유효성 검사 필요
    val password: String,
    val phoneNumber: String,
    val verificationCode: String,
    val dong: dong,
    val branch: branch,
    val recommender: String,
    val introduction: String,
    val specialty: specialty
)