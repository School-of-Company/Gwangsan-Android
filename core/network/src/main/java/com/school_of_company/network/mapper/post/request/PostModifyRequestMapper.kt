package com.school_of_company.network.mapper.post.request

import com.school_of_company.model.post.request.PostModifyRequestModel
import com.school_of_company.network.dto.post.request.PostModifyRequest

fun PostModifyRequestModel.toDto(): PostModifyRequest
= PostModifyRequest (
    type = type,
    mode = mode,
    title = title,
    content = content,
    gwangsan = gwangsan,
    imageIds = imageIds
)
