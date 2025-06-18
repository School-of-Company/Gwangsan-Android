package com.school_of_company.network.dto.auth.requset

import com.school_of_company.model.enum.branch
import com.school_of_company.model.enum.dong
import com.school_of_company.model.enum.specialty
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest(
    @Json(name = "name") val name: String,
    @Json(name = "password") val password: String,
    @Json(name = "phoneNumber") val phoneNumber: String,
    @Json(name = "verificationCode") val verificationCode: String,
    @Json(name = "dong") val dong: dong,
    @Json(name = "branch") val branch: branch,
    @Json(name = "recommender") val recommender: String,
    @Json(name = "specialty") val specialty: specialty,
    @Json(name = "introduction") val introduction: String
)
