package com.school_of_company.network.dto.auth.requset

import android.content.Context
import com.school_of_company.model.enum.OsType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.UUID

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "nickname") val nickname: String,
    @Json(name = "password") val password: String,
    @Json(name = "deviceToken") val deviceToken: String,
    @Json(name = "deviceId") val deviceId: String,
    @Json(name = "osType") val osType: OsType = OsType.ANDROID // 고정
)