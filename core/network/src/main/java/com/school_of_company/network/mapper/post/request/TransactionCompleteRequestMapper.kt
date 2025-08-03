package com.school_of_company.network.mapper.post.request

import com.school_of_company.model.post.request.PostAllRequestModel
import com.school_of_company.model.post.request.TransactionCompleteRequestModel
import com.school_of_company.network.dto.post.request.PostAllRequest
import com.school_of_company.network.dto.post.request.TransactionCompleteRequest

fun TransactionCompleteRequestModel.toDto(): TransactionCompleteRequest =
    TransactionCompleteRequest (
        productId = productId,
        otherMemberId = otherMemberId
    )
