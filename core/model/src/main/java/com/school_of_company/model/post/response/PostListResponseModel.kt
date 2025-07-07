package com.school_of_company.model.post.response

data class PostListResponseModel(
    val body: List<Post>
)

data class Post(
    val id: Int,
    val type: String,
    val mode: String,
    val title: String,
    val content: String,
    val gwangsan: Int,
    val imageUrls: ImageUrls
)

data class ImageUrls(
    val images: List<Image>
)

data class Image(
    val imageId: Int,
    val imageUrl: String
)
