package com.school_of_company.model.member.response

import kotlinx.collections.immutable.PersistentList

data class GetAllMemberResponseModel(
    val memberId: Long,
    val nickname: String,
    val placeName: String,
    val light: Int,
    val description: String,
    val specialties: PersistentList<String>
)
