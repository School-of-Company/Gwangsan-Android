package com.school_of_company.network.dto.auth.requset

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SmsVerifyCodeRequest(
    @Json(name = "phoneNumber") val phoneNumber: String,
    @Json(name = "code") val code: String
)
