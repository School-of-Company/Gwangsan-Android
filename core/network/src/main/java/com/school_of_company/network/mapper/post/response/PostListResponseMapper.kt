package com.school_of_company.network.mapper.post.response

import com.school_of_company.model.post.response.Image
import com.school_of_company.model.post.response.Post
import com.school_of_company.network.dto.post.response.ImageDto
import com.school_of_company.network.dto.post.response.PostDto

fun PostDto.toModel(): Post {
    return Post(
        id = this.id,
        type = this.type,
        mode = this.mode,
        title = this.title,
        content = this.content,
        gwangsan = this.gwangsan,
        images = this.images.map { it.toModel() }
    )
}

fun ImageDto.toModel(): Image {
    return Image(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )
}