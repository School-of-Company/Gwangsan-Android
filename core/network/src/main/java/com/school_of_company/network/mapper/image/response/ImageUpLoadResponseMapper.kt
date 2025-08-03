package com.school_of_company.network.mapper.image.response

import com.school_of_company.model.image.response.ImageUpLoadResponseModel
import com.school_of_company.network.dto.image.response.ImageUpLoadResponse

fun ImageUpLoadResponse.toModel() : ImageUpLoadResponseModel =
    ImageUpLoadResponseModel(
        imageId = this.imageId,
        imageUrl = this.imageUrl
    )
