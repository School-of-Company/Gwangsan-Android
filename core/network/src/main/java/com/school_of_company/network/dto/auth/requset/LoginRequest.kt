package com.school_of_company.network.dto.auth.requset

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "nickname") val nickname: String,
    @Json(name = "password") val password: String,
    @Json(name = "deviceToken") val deviceToken: String,
    @Json(name = "deviceId") val deviceId: String,
    @Json(name = "osType") val osType: String = "ANDROID"
)