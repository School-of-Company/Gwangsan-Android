package com.school_of_company.model.auth.request

import com.school_of_company.model.enum.OsType
import java.util.UUID

data class LoginRequestModel(
    val nickname: String,
    val password: String,
    val deviceToken: String,
    val deviceId: String,
    val osType: OsType = OsType.ANDROID
)