package com.school_of_company.content.ui.mapper

import com.school_of_company.content.ui.model.ImageUi
import com.school_of_company.content.ui.model.MemberUi
import com.school_of_company.content.ui.model.PostUi
import com.school_of_company.model.post.response.Image
import com.school_of_company.model.post.response.Member
import com.school_of_company.model.post.response.Post
import kotlinx.collections.immutable.toPersistentList

/**
 * API 응답 → UI 모델 변환
 */

fun Post.toUi() : PostUi =
    PostUi(
        id = this.id,
        title = this.title,
        content = this.content,
        gwangsan = this.gwangsan,
        type = this.type,
        mode = this.mode,
        member = this.member.toUi(),
        images = this.images.map { it.toUi() }.toPersistentList(),
        isMine = this.isMine,
        isCompletable = this.isCompletable,
        isCompleted = this.isCompleted
    )

fun Member.toUi() : MemberUi =
    MemberUi(
        memberId = this.memberId,
        nickname = this.nickname,
        placeName = this.placeName,
        light = this.light
    )

fun Image.toUi() : ImageUi =
    ImageUi(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )