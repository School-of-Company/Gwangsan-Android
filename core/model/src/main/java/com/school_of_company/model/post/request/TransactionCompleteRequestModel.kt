package com.school_of_company.model.post.request

data class TransactionCompleteRequestModel(
    val productId: Long,
    val otherMemberId: Long
)