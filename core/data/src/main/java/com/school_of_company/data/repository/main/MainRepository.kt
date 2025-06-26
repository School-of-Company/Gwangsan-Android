package com.school_of_company.data.repository.main

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.model.main.response.MainListResponseModel
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun allPostGeT(
        type: Type,
        mode: Mode,
    ): Flow<List<MainListResponseModel>>
}