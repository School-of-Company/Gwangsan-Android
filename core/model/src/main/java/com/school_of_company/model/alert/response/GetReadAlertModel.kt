package com.school_of_company.model.alert.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class GetReadAlertModel(
    val unread: Boolean,
)