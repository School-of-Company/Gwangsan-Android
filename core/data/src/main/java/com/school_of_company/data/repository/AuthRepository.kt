package com.school_of_company.data.repository

import com.school_of_company.model.auth.request.LoginRequestModel
import com.school_of_company.model.auth.request.SignUpRequestModel
import com.school_of_company.model.auth.response.LoginResponseModel
import com.school_of_company.network.dto.auth.requset.LoginRequest
import com.school_of_company.network.dto.auth.requset.SignUpRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signUp(body: SignUpRequestModel) : Flow<Unit>

    fun signIn(body: LoginRequestModel) : Flow<LoginResponseModel>

    fun tokenRefresh(refreshToken: String) : Flow<LoginResponseModel>

    fun logout() : Flow<Unit>

    fun signLogout() : Flow<Unit>

    fun getRefreshToken() : Flow<String>

    suspend fun saveToken(token: LoginResponseModel)

    suspend fun deleteTokenData()
}