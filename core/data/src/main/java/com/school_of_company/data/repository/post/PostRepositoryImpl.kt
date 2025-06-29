package com.school_of_company.data.repository.post

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.network.datasource.post.PostDataSource
import com.school_of_company.network.dto.post.request.PostWriteRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor (
    private val postDataSource: PostDataSource
) : PostRepository {
    override fun writePostInformation(
        type: Type,
        mode: Mode,
        request: PostWriteRequest
    ): Flow<Unit> {
        return postDataSource.writePostInformation(
            type = type,
            mode = mode,
            request = request
        )
    }

    override fun modifyPostInformation(
        type: Type,
        mode: Mode,
        request: PostWriteRequest
    ): Flow<Unit> {
        return postDataSource.modifyPostInformation(
            type = type,
            mode = mode,
            request = request
        )
    }
}
