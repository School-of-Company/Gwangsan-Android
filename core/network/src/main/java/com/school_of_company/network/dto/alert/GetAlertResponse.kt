package com.school_of_company.network.dto.alert

import com.school_of_company.model.enum.AlertType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.datetime.LocalDateTime

@JsonClass(generateAdapter = true)
data class GetAlertResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "sourceId") val sourceId: Long,
    @Json(name = "content") val content: String,
    @Json(name = "sendMemberId") val sendMemberId: Long? = null,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "alertType") val alertType: AlertType,
    @Json(name = "images") val images: List<GetAlertImages>
)

@JsonClass(generateAdapter = true)
data class GetAlertImages(
    @Json(name = "imageId") val imageId: Long,
    @Json(name = "imageUrl") val imageUrl: String
)