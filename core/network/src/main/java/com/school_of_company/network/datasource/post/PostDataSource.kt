package com.school_of_company.network.datasource.post

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.network.dto.post.request.PostWriteRequest
import kotlinx.coroutines.flow.Flow

interface PostDataSource {

    fun writePostInformation(
        type: Type,
        mode: Mode,
        request: PostWriteRequest
    ): Flow<Unit>

    fun modifyPostInformation(
        type: Type,
        mode: Mode,
        request: PostWriteRequest
    ): Flow<Unit>
}
