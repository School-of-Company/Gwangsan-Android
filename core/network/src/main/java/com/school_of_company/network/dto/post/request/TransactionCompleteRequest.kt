package com.school_of_company.network.dto.post.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionCompleteRequest(
    @Json(name = "productId") val productId: Long,
    @Json(name = "otherMemberId") val otherMemberId: Long
)
