package com.school_of_company.model.enum

import com.squareup.moshi.Json

enum class Type {
    @Json(name = "OBJECT") OBJECT,
    @Json(name = "SERVICE") SERVICE
}