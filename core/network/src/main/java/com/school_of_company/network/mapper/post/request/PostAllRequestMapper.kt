package com.school_of_company.network.mapper.post.request

import com.school_of_company.model.post.request.PostAllRequestModel
import com.school_of_company.network.dto.post.request.PostAllRequest

fun PostAllRequestModel.toDto(): PostAllRequest =
    PostAllRequest (
    type = type,
    mode = mode,
    title = title,
    content = content,
    gwangsan = gwangsan,
    imageIds = imageIds
)
