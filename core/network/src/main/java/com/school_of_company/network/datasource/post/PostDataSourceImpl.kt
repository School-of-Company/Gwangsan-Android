package com.school_of_company.network.datasource.post

import com.school_of_company.network.api.PostAPI
import com.school_of_company.network.dto.post.request.PostAllRequest
import com.school_of_company.network.dto.post.request.TransactionCompleteRequest
import com.school_of_company.network.dto.post.response.AllPostDto
import com.school_of_company.network.dto.post.response.PostDto
import com.school_of_company.network.dto.post.response.PostModifyResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(
    private val postAPI: PostAPI
) : PostDataSource {
    override fun writePostInformation(body: PostAllRequest): Flow<Unit> =
        performApiRequest { postAPI.writePostInformation(body = body) }

    override fun modifyPostInformation(
        postId: Long,
        body: PostAllRequest
    ): Flow<Unit> =
        performApiRequest { postAPI.modifyPostInformation(
            postId = postId,
            body = body
        ) }

    override fun getSpecificInformation(postId: Long): Flow<PostDto> =
        performApiRequest { postAPI.getSpecificInformation(postId = postId) }

    override fun getAllPostInformation(type: String, mode: String): Flow<List<AllPostDto>> =
        performApiRequest { postAPI.getAllPostInformation(
            type = type,
            mode = mode
        ) }

    override fun getMyPostInformation(type: String?, mode: String?): Flow<List<AllPostDto>> =
        performApiRequest { postAPI.getMyPostInformation(
            type = type,
            mode = mode
        ) }

    override fun deletePostInformation(postId: Long): Flow<Unit> =
        performApiRequest { postAPI.deletePostInformation(postId = postId) }

    override fun transactionComplete(body: TransactionCompleteRequest): Flow<Unit> =
        performApiRequest { postAPI.transactionComplete(body = body) }

    override fun otherPostInformation(
        type: String?,
        mode: String?,
        memberId: Long,
        ): Flow<List<AllPostDto>> =
        performApiRequest {
            postAPI.otherPostInformation(
                type = type,
                mode = mode,
                memberId = memberId
            )
        }
}
