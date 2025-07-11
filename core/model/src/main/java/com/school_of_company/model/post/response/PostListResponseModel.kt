package com.school_of_company.model.post.response

data class Post(
    val id: Long,
    val title: String,
    val content: String,
    val gwangsan: Int,
    val type: String,
    val mode: String,
    val member: Member,
    val images: List<Image>
)

data class Member(
    val memberId: Long,
    val nickname: String,
    val placeName: String,
    val light: Int
)

data class Image(
    val imageId: Long,
    val imageUrl: String
)
