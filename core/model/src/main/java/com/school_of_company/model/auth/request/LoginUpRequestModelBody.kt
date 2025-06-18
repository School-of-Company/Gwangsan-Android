package com.school_of_company.model.auth.request

data class LoginUpRequestModelBody(
    val nickname: String,
    val password: String,
)