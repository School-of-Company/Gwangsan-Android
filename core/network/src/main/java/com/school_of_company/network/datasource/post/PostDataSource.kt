package com.school_of_company.network.datasource.post

import com.school_of_company.network.dto.post.request.PostAllRequest
import com.school_of_company.network.dto.post.request.TransactionCompleteRequest
import com.school_of_company.network.dto.post.response.AllPostDto
import com.school_of_company.network.dto.post.response.PostDto
import com.school_of_company.network.dto.post.response.PostModifyResponse
import kotlinx.coroutines.flow.Flow

interface PostDataSource {

    fun writePostInformation(body: PostAllRequest) : Flow<Unit>
    fun modifyPostInformation(
        postId: Long,
        body: PostAllRequest
    ) : Flow<Unit>
    fun getSpecificInformation(postId: Long) : Flow<PostDto>
    fun getAllPostInformation(
        type: String,
        mode: String
    ) : Flow<List<AllPostDto>>
    fun getMyPostInformation(
        type: String? = null,
        mode: String? = null
    ) : Flow<List<AllPostDto>>
    fun deletePostInformation(postId: Long) : Flow<Unit>
    fun transactionComplete(body: TransactionCompleteRequest) : Flow<Unit>
    fun otherPostInformation(
        type: String? = null,
        mode: String? = null,
        memberId: Long
    ) : Flow<List<AllPostDto>>
}
