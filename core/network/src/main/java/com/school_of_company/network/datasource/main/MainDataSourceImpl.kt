package com.school_of_company.network.datasource.main

import android.util.Log
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.network.api.AuthAPI
import com.school_of_company.network.api.MainAPI
import com.school_of_company.network.dto.main.reponse.MainListResponse
import com.school_of_company.network.dto.reponse.LoginResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainDataSourceImpl @Inject constructor(
    private val mainAPI: MainAPI
) : MainDataSource {
    override fun allPostGet(
        type: Type,
        mode: Mode,
    ): Flow<List<MainListResponse>> {
        return performApiRequest {
            mainAPI.allPostGet(
                type = type,
                mode = mode
            )
        }
    }
}