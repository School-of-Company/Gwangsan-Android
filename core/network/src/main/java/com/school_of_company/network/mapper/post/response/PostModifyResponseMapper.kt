package com.school_of_company.network.mapper.post.response

import com.school_of_company.model.post.response.PostModifyResponseModel
import com.school_of_company.network.dto.post.response.PostModifyResponse

fun PostModifyResponse.toModel(): PostModifyResponseModel =
    PostModifyResponseModel(
        message = this.message
    )