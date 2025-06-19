package com.school_of_company.network.datasource.auth

import com.school_of_company.network.dto.auth.requset.LoginRequest
import com.school_of_company.network.dto.auth.requset.SignUpRequest
import com.school_of_company.network.dto.reponse.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    suspend fun signUp(body: SignUpRequest): Flow<Unit>

    suspend fun login(body: LoginRequest): Flow<LoginResponse>

    suspend fun tokenRefresh(refreshToken: String): Flow<LoginResponse>

    suspend fun signLogout(): Flow<Unit>

    suspend fun logout(): Flow<Unit>
}