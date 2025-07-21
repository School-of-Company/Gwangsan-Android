package com.school_of_company.network.mapper.post.response

import com.school_of_company.model.post.response.AllImage
import com.school_of_company.model.post.response.AllMember
import com.school_of_company.model.post.response.AllPost
import com.school_of_company.network.dto.post.response.AllImageDto
import com.school_of_company.network.dto.post.response.AllMemberDto
import com.school_of_company.network.dto.post.response.AllPostDto

fun AllPostDto.toModel(): AllPost {
    return AllPost(
        id = this.id,
        type = this.type,
        mode = this.mode,
        title = this.title,
        content = this.content,
        gwangsan = this.gwangsan,
        member = this.member.toModel(),
        images = this.images.map { it.toModel() },
    )
}

fun AllMemberDto.toModel(): AllMember {
    return AllMember(
        memberId = this.memberId,
        nickname = this.nickname,
        placeName = this.placeName,
        light = this.light
    )
}

fun AllImageDto.toModel(): AllImage {
    return AllImage(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )
}