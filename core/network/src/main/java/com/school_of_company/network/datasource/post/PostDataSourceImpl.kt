package com.school_of_company.network.datasource.post

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.network.api.PostAPI
import com.school_of_company.network.dto.post.request.PostWriteRequest
import com.school_of_company.network.dto.post.response.PostModifyResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(
    private val postAPI: PostAPI
) : PostDataSource {

    override fun writePostInformation(
        token: String,
        type: Type,
        mode: Mode,
        request: PostWriteRequest
    ): Flow<Unit> =
        performApiRequest {
            postAPI.writePostInformation(token, type, mode, request)
        }

    override fun modifyPostInformation(
        token: String,
        type: Type,
        mode: Mode,
        request: PostWriteRequest
    ): Flow<PostModifyResponse> =
        performApiRequest {
            postAPI.modifyPostInformation(token, type, mode, request)
        }
}