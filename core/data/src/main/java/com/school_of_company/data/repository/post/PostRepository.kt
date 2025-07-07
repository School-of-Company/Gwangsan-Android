package com.school_of_company.data.repository.post

import com.school_of_company.model.post.request.PostAllRequestModel
import com.school_of_company.model.post.response.PostListResponseModel
import com.school_of_company.network.dto.post.response.PostModifyResponse
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun writePostInformation(body: PostAllRequestModel) : Flow<Unit>
    fun modifyPostInformation(
        postId: Long,
        body: PostAllRequestModel
    ) : Flow<PostModifyResponse>
    fun getSpecificInformation() : Flow<PostListResponseModel>
    fun getAllPostInformation(
        type: String,
        mode: String
    ) : Flow<PostListResponseModel>
    fun getMyPostInformation(
        type: String,
        mode: String
    ) : Flow<PostListResponseModel>
    fun deletePostInformation(postId: Long) : Flow<Unit>
}