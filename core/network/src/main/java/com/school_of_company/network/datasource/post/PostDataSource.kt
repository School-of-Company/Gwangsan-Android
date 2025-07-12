package com.school_of_company.network.datasource.post

import com.school_of_company.network.dto.post.request.PostAllRequest
import com.school_of_company.network.dto.post.response.PostDto
import com.school_of_company.network.dto.post.response.PostModifyResponse
import kotlinx.coroutines.flow.Flow

interface PostDataSource {

    fun writePostInformation(body: PostAllRequest) : Flow<Unit>
    fun modifyPostInformation(
        postId: Long,
        body: PostAllRequest
    ) : Flow<PostModifyResponse>
    fun getSpecificInformation(postId: Long) : Flow<PostDto>
    fun getAllPostInformation(
        type: String,
        mode: String
    ) : Flow<List<PostDto>>
    fun getMyPostInformation(
        type: String? = null,
        mode: String? = null
    ) : Flow<List<PostDto>>
    fun deletePostInformation(postId: Long) : Flow<Unit>
}
