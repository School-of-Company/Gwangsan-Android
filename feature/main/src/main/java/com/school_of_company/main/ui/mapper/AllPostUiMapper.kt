package com.school_of_company.main.ui.mapper

import com.school_of_company.main.ui.model.AllImageUi
import com.school_of_company.main.ui.model.AllMemberUi
import com.school_of_company.main.ui.model.AllPostUi
import com.school_of_company.model.post.response.AllImage
import com.school_of_company.model.post.response.AllMember
import com.school_of_company.model.post.response.AllPost
import kotlinx.collections.immutable.toPersistentList

/**
 * API 응답 → UI 모델 변환
 */


fun AllPost.toUi() : AllPostUi =
    AllPostUi(
        id = this.id,
        title = this.title,
        content = this.content,
        gwangsan = this.gwangsan,
        type = this.type,
        mode = this.mode,
        member = this.member.toUi(),
        images = this.images.map { it.toUi() }.toPersistentList()
    )

fun AllMember.toUi() : AllMemberUi =
    AllMemberUi(
        memberId = this.memberId,
        nickname = this.nickname,
        placeName = this.placeName,
        light = this.light
    )

fun AllImage.toUi() : AllImageUi =
    AllImageUi(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )