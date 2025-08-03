package com.school_of_company.network.datasource.image

import com.school_of_company.network.dto.image.response.ImageUpLoadResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ImageDataSource {
    fun imageUpLoad(image: MultipartBody.Part) : Flow<ImageUpLoadResponse>
}