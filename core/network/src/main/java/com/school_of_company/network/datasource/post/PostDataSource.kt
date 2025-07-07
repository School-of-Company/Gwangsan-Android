package com.school_of_company.network.datasource.post

import com.school_of_company.network.dto.post.request.PostAllRequest
import com.school_of_company.network.dto.post.response.PostListResponse
import com.school_of_company.network.dto.post.response.PostModifyResponse
import kotlinx.coroutines.flow.Flow

interface PostDataSource {

    fun writePostInformation(body: PostAllRequest) : Flow<Unit>
    fun modifyPostInformation(
        postId: Long,
        body: PostAllRequest
    ) : Flow<PostModifyResponse>
    fun getSpecificInformation() : Flow<PostListResponse>
    fun getAllPostInformation(
        type: String,
        mode: String
    ) : Flow<PostListResponse>
    fun getMyPostInformation(
        type: String,
        mode: String
    ) : Flow<PostListResponse>
    fun deletePostInformation(postId: Long) : Flow<Unit>
}
