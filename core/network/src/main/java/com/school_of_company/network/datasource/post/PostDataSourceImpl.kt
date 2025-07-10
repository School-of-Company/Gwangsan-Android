package com.school_of_company.network.datasource.post

import com.school_of_company.network.api.PostAPI
import com.school_of_company.network.dto.post.request.PostAllRequest
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
    ): Flow<PostModifyResponse> =
        performApiRequest { postAPI.modifyPostInformation(
            postId = postId,
            body = body
        ) }

    override fun getSpecificInformation(): Flow<List<PostDto>> =
        performApiRequest { postAPI.getSpecificInformation() }

    override fun getAllPostInformation(type: String, mode: String): Flow<List<PostDto>> =
        performApiRequest { postAPI.getAllPostInformation(
            type = type,
            mode = mode
        ) }

    override fun getMyPostInformation(type: String, mode: String): Flow<List<PostDto>> =
        performApiRequest { postAPI.getMyPostInformation(
            type = type,
            mode = mode
        ) }

    override fun deletePostInformation(postId: Long): Flow<Unit> =
        performApiRequest { postAPI.deletePostInformation(postId = postId) }
}
