package com.school_of_company.network.api

import com.school_of_company.network.dto.post.request.PostAllRequest
import com.school_of_company.network.dto.post.response.PostDto
import com.school_of_company.network.dto.post.response.PostModifyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.Query

interface PostAPI {

    @POST("/api/post")
    suspend fun writePostInformation(
        @Body body: PostAllRequest
    )

    @PATCH("/api/post/{post_id}")
    suspend fun modifyPostInformation(
        @Path("post_id") postId: Long,
        @Body body: PostAllRequest
    ) : PostModifyResponse

    @GET("/api/post/{post_id}")
    suspend fun getSpecificInformation() : List<PostDto>

    @GET("/api/post")
    suspend fun getAllPostInformation(
        @Query("type") type: String,
        @Query("mode") mode: String
    ) : List<PostDto>

    @GET("/api/post/current")
    suspend fun getMyPostInformation(
        @Query("type") type: String? = null,
        @Query("mode") mode: String? = null,
    ) : List<PostDto>

    @DELETE("/api/post/{post_id}")
    suspend fun deletePostInformation(
        @Path("post_id") postId: Long
    )
}
