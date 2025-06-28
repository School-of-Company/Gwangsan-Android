package com.school_of_company.network.datasource.post

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.network.dto.post.request.PostWriteRequest
import com.school_of_company.network.dto.post.response.PostModifyResponse
import kotlinx.coroutines.flow.Flow

interface PostDataSource {

    fun writePostInformation(
        token: String,
        type: Type,
        mode: Mode,
        request: PostWriteRequest
    ): Flow<Unit>

    fun modifyPostInformation(
        token: String,
        type: Type,
        mode: Mode,
        request: PostWriteRequest
    ): Flow<PostModifyResponse>
}
