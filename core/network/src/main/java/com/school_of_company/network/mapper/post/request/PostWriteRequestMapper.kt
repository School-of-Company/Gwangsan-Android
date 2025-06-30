package com.school_of_company.network.mapper.post.request

import com.school_of_company.model.post.request.PostWriteRequestModel
import com.school_of_company.network.dto.post.request.PostWriteRequest

fun PostWriteRequestModel.toDto(): PostWriteRequest =
    PostWriteRequest (
    type = type,
    mode = mode,
    title = title,
    content = content,
    gwangsan = gwangsan,
    imageIds = imageIds
)
