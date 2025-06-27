package com.school_of_company.data.repository.main

import android.util.Log
import com.school_of_company.data.repository.auth.AuthRepository
import com.school_of_company.datastore.datasource.AuthTokenDataSource
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.model.main.response.MainListResponseModel
import com.school_of_company.network.datasource.auth.AuthDataSource
import com.school_of_company.network.datasource.main.MainDataSource
import com.school_of_company.network.mapper.main.reponse.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject


class MainRepositoryImpl @Inject constructor(
    private val mainDataSource: MainDataSource,
) : MainRepository {
    override fun allPostGet(
        type: Type,
        mode: Mode
    ): Flow<List<MainListResponseModel>> {
        Log.d("MainRepositoryImpl", "📡 allPostGeT called with type=$type, mode=$mode")

        return mainDataSource.allPostGet(
            type = type,
            mode = mode,
        ).transform { response ->
            Log.d("MainRepositoryImpl", "📦 Response size: ${response.size}")
            emit(response.map { it.toModel() })
        }
    }
}