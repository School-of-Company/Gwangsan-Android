package com.school_of_company.data.repository.post

import com.school_of_company.model.post.request.PostAllRequestModel
import com.school_of_company.model.post.request.TransactionCompleteRequestModel
import com.school_of_company.model.post.response.AllPost
import com.school_of_company.model.post.response.Post
import com.school_of_company.network.dto.post.response.PostModifyResponse
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun writePostInformation(body: PostAllRequestModel) : Flow<Unit>
    fun modifyPostInformation(
        postId: Long,
        body: PostAllRequestModel
    ) : Flow<Unit>
    fun getSpecificInformation(postId: Long) : Flow<Post>
    fun getAllPostInformation(
        type: String,
        mode: String
    ) : Flow<List<AllPost>>
    fun getMyPostInformation(
        type: String? = null,
        mode: String? = null
    ) : Flow<List<AllPost>>
    fun deletePostInformation(postId: Long) : Flow<Unit>
    fun transactionComplete(body: TransactionCompleteRequestModel) : Flow<Unit>
    fun otherPostInformation(
        type: String? = null,
        mode: String? = null,
        memberId: Long
    ) : Flow<List<AllPost>>
}