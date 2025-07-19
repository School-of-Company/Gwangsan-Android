package com.school_of_company.network.dto.webhook.info

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CrashInfo(
    val appVersion: String,
    val androidVersion: String,
    val deviceModel: String,
    val timestamp: String,
    val stackTrace: String,
    val exceptionType: String,
    val exceptionMessage: String?
)