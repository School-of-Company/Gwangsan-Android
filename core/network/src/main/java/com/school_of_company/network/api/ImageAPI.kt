package com.school_of_company.network.api

import com.school_of_company.network.dto.image.response.ImageUpLoadResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageAPI {

    @Multipart
    @POST("/api/image")
    suspend fun imageUpLoad(
        @Part file: MultipartBody.Part
    ) : ImageUpLoadResponse
}