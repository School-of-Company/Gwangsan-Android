package com.school_of_company.model.post.request

data class PostAllRequestModel(
    val type: String,
    val mode: String,
    val title: String,
    val content: String,
    val gwangsan: Int,
    val imageIds: List<Long>
)
