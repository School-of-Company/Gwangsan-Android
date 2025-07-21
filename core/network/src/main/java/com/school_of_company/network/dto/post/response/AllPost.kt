package com.school_of_company.network.dto.post.response

import com.school_of_company.model.post.response.AllImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllPostDto(
    @Json(name = "id") val id: Long,
    @Json(name = "type") val type: String,
    @Json(name = "mode") val mode: String,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "gwangsan") val gwangsan: Int,
    @Json(name = "member") val member: AllMemberDto,
    @Json(name = "images") val images: List<AllImageDto>,
)

@JsonClass(generateAdapter = true)
data class AllMemberDto(
    @Json(name = "memberId") val memberId: Long,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "placeName") val placeName: String,
    @Json(name = "light") val light: Int
)

@JsonClass(generateAdapter = true)
data class AllImageDto(
    @Json(name = "imageId") val imageId: Long,
    @Json(name = "imageUrl") val imageUrl: String
)

