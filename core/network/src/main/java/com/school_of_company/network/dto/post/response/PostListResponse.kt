package com.school_of_company.network.dto.post.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostDto(
    @Json(name = "id") val id: Long,
    @Json(name = "type") val type: String,
    @Json(name = "mode") val mode: String,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "gwangsan") val gwangsan: Int,
    @Json(name = "member") val member: MemberDto,
    @Json(name = "images") val images: List<ImageDto>
)

@JsonClass(generateAdapter = true)
data class MemberDto(
    @Json(name = "memberId") val memberId: Long,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "placeName") val placeName: String,
    @Json(name = "light") val light: Int
)

@JsonClass(generateAdapter = true)
data class ImageDto(
    @Json(name = "imageId") val imageId: Long,
    @Json(name = "imageUrl") val imageUrl: String
)
