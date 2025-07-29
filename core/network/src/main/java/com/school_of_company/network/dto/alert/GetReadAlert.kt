package com.school_of_company.network.dto.alert

import com.school_of_company.model.enum.AlertType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetReadAlert(
    @Json(name = "unread") val unread: Boolean,
)