package com.school_of_company.model.enum

import com.squareup.moshi.Json

enum class OsType {
    @Json(name = "ANDROID") ANDROID,
    @Json(name = "IOS") IOS
}