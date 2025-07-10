package com.school_of_company.network.dto.auth.requset

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest(
    @Json(name = "name") val name: String,
    @Json(name = "nickname") val nickname: String, // 한글만
    @Json(name = "password") val password: String,
    @Json(name = "phoneNumber") val phoneNumber: String,
    @Json(name = "dongName") val dongName: String,
    @Json(name = "placeName") val placeName: String,
    @Json(name = "recommender") val recommender: String,
    @Json(name = "specialties") val specialties: List<String>,
    @Json(name = "description") val description: String
)
