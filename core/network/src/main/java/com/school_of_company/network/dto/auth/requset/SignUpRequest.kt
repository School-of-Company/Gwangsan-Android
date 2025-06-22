package com.school_of_company.network.dto.auth.requset

import com.school_of_company.model.enum.Branch
import com.school_of_company.model.enum.Dong
import com.school_of_company.model.enum.Specialty
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpRequest(
    @Json(name = "name") val name: String,
    @Json(name = "password") val password: String,
    @Json(name = "phoneNumber") val phoneNumber: String,
    @Json(name = "verificationCode") val verificationCode: String,
    @Json(name = "dong") val dong: Dong,
    @Json(name = "branch") val branch: Branch,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "recommender") val recommender: String,
    @Json(name = "specialty") val specialty: Specialty,
    @Json(name = "introduction") val introduction: String
)
