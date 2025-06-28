package com.school_of_company.network.mapper.main.reponse

import com.school_of_company.model.main.response.MainListResponseModel
import com.school_of_company.network.dto.main.reponse.MainListResponse

fun MainListResponse.toModel(): MainListResponseModel =
    MainListResponseModel(
        title = title,
        content = content,
        gwangsan = gwangsan,
        type = type,
        mode = mode,
        imageUrls = imageUrls,
        id = id
    )
