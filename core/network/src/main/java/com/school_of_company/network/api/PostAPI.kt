package com.school_of_company.network.api

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.network.dto.post.request.PostWriteRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface PostAPI {

    @POST("/api/post")
    suspend fun writePostInformation(
        @Query("type") type: Type,
        @Query("mode") mode: Mode,
        @Body request: PostWriteRequest
    )

    @POST("/api/post/modify")
    suspend fun modifyPostInformation(
        @Query("type") type: Type,
        @Query("mode") mode: Mode,
        @Body request: PostWriteRequest
    )
}
