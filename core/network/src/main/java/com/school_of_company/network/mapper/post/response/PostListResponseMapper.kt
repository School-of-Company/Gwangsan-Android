package com.school_of_company.network.mapper.post.response

import com.school_of_company.model.post.response.Image
import com.school_of_company.model.post.response.Member
import com.school_of_company.model.post.response.Post
import com.school_of_company.network.dto.post.response.ImageDto
import com.school_of_company.network.dto.post.response.MemberDto
import com.school_of_company.network.dto.post.response.PostDto
import kotlinx.collections.immutable.toPersistentList


fun PostDto.toModel(): Post {
    return Post(
        id = this.id,
        type = this.type,
        mode = this.mode,
        title = this.title,
        content = this.content,
        gwangsan = this.gwangsan,
        member = this.member.toModel(),
        images = this.images.map { it.toModel() }.toPersistentList(),
        isMine = this.isMine,
        isCompletable = this.isCompletable,
        isCompleted = this.isCompleted
    )
}

fun MemberDto.toModel(): Member {
    return Member(
        memberId = this.memberId,
        nickname = this.nickname,
        placeName = this.placeName,
        light = this.light
    )
}

fun ImageDto.toModel(): Image {
    return Image(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )
}