package com.school_of_company.model.post.response

data class Post(
    val id: Long,
    val type: String,
    val mode: String,
    val title: String,
    val content: String,
    val gwangsan: Int,
    val images: List<Image>
)

data class Image(
    val imageId: Long,
    val imageUrl: String
)
