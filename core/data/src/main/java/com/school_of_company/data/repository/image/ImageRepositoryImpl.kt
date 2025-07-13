package com.school_of_company.data.repository.image

import com.school_of_company.model.image.response.ImageUpLoadResponseModel
import com.school_of_company.network.datasource.image.ImageDataSource
import com.school_of_company.network.mapper.image.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val dataSource: ImageDataSource
) : ImageRepository {
    override fun imageUpLoad(image: MultipartBody.Part): Flow<ImageUpLoadResponseModel> {
        return dataSource.imageUpLoad(image = image).map { it.toModel() }
    }
}