package com.school_of_company.model.auth.request

data class SmsVerifyCodeRequestModel(
    val phoneNumber: String,
    val code: String
)
