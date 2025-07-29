package com.school_of_company.model.enum

import com.squareup.moshi.Json

enum class Mode {
    @Json(name = "GIVER") GIVER,
    @Json(name = "RECEIVER") RECEIVER
}