package com.school_of_company.model.post.response

import kotlinx.collections.immutable.PersistentList

data class AllPost(
    val id: Long,
    val title: String,
    val content: String,
    val gwangsan: Int,
    val type: String,
    val mode: String,
    val member: AllMember,
    val images: PersistentList<AllImage>,
)

data class AllMember(
    val memberId: Long,
    val nickname: String,
    val placeName: String,
    val light: Int
)

data class AllImage(
    val imageId: Long,
    val imageUrl: String
)