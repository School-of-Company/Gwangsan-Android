package com.school_of_company.network.mapper.post.response

import com.school_of_company.model.post.response.Image
import com.school_of_company.model.post.response.ImageUrls
import com.school_of_company.model.post.response.Post
import com.school_of_company.model.post.response.PostListResponseModel
import com.school_of_company.network.dto.post.response.ImageDto
import com.school_of_company.network.dto.post.response.ImageUrlsDto
import com.school_of_company.network.dto.post.response.PostDto
import com.school_of_company.network.dto.post.response.PostListResponse

fun PostListResponse.toModel(): PostListResponseModel {
    return PostListResponseModel(
        body = this.body.map { it.toModel() }
    )
}

fun PostDto.toModel(): Post {
    return Post(
        id = this.id.toInt(),
        type = this.type,
        mode = this.mode,
        title = this.title,
        content = this.content,
        gwangsan = this.gwangsan,
        imageUrls = this.imageUrls.toModel()
    )
}

fun ImageUrlsDto.toModel(): ImageUrls {
    return ImageUrls(
        images = this.images.map { it.toModel() }
    )
}

fun ImageDto.toModel(): Image {
    return Image(
        imageId = this.imageId.toInt(),
        imageUrl = this.imageUrl
    )
}