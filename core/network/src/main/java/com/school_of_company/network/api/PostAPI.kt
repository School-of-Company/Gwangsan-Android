package com.school_of_company.network.api

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.network.dto.post.request.PostWriteRequest
import com.school_of_company.network.dto.post.response.PostModifyResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PostAPI {

    @POST("/api/post")
    suspend fun writePostInformation(
        @Header("Authorization") token: String,
        @Query("type") type: Type,
        @Query("mode") mode: Mode,
        @Body request: PostWriteRequest
    )

    @POST("/api/post/modify")
    suspend fun modifyPostInformation(
        @Header("Authorization") token: String,
        @Query("type") type: Type,
        @Query("mode") mode: Mode,
        @Body request: PostWriteRequest
    ): PostModifyResponse
}
