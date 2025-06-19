package com.school_of_company.model.auth.request

data class LoginRequestModel(
    val nickname: String,
    val password: String,
)