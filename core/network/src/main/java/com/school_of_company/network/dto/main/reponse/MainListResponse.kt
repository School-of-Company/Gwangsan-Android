package com.school_of_company.network.dto.main.reponse

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainListResponse(
    @Json(name = "id") val id: Number,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "gwangsan") val gwangsan: Number,
    @Json(name = "type") val type: Type,
    @Json(name = "mode") val mode: Mode,
    @Json(name = "imageUrls") val imageUrls: String,
)