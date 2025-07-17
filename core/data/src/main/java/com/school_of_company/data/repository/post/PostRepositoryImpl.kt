package com.school_of_company.data.repository.post

import com.school_of_company.model.post.request.PostAllRequestModel
import com.school_of_company.model.post.request.TransactionCompleteRequestModel
import com.school_of_company.model.post.response.Post
import com.school_of_company.network.datasource.post.PostDataSource
import com.school_of_company.network.dto.post.response.PostModifyResponse
import com.school_of_company.network.mapper.post.request.toDto
import com.school_of_company.network.mapper.post.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor (
    private val postDataSource: PostDataSource
) : PostRepository {
    override fun writePostInformation(body: PostAllRequestModel): Flow<Unit> {
        return postDataSource.writePostInformation(body = body.toDto())
    }

    override fun modifyPostInformation(
        postId: Long,
        body: PostAllRequestModel
    ): Flow<Unit> {
        return postDataSource.modifyPostInformation(
            postId = postId,
            body = body.toDto()
        )
    }

    override fun getSpecificInformation(postId: Long): Flow<Post> {
        return postDataSource.getSpecificInformation(postId = postId).map { it.toModel() }
    }

    override fun getAllPostInformation(
        type: String,
        mode: String
    ): Flow<List<Post>> {
        return postDataSource.getAllPostInformation(
            type = type,
            mode = mode
        ).map { it.map { list -> list.toModel() } }
    }

    override fun getMyPostInformation(
        type: String?,
        mode: String?
    ): Flow<List<Post>> {
        return postDataSource.getMyPostInformation(
            type = type,
            mode = mode
        ).map { it.map { list -> list.toModel() } }
    }

    override fun deletePostInformation(postId: Long): Flow<Unit> {
        return postDataSource.deletePostInformation(postId = postId)
    }

    override fun transactionComplete(body: TransactionCompleteRequestModel): Flow<Unit> {
        return postDataSource.transactionComplete(body = body.toDto())
    }

    override fun otherPostInformation(
        type: String?,
        mode: String?,
        memberId: Long
    ): Flow<List<Post>> {
        return postDataSource.otherPostInformation(
            type = type,
            mode = mode,
            memberId = memberId
        ).map {it.map { list -> list.toModel() }}
    }
}
