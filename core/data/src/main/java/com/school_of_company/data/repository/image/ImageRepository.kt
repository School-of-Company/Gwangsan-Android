package com.school_of_company.data.repository.image

import com.school_of_company.model.image.response.ImageUpLoadResponseModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ImageRepository {
    fun imageUpLoad(image: MultipartBody.Part) : Flow<ImageUpLoadResponseModel>
}