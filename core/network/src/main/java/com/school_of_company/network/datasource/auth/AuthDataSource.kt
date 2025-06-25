package com.school_of_company.network.datasource.auth

import com.school_of_company.network.dto.auth.requset.LoginRequest
import com.school_of_company.network.dto.auth.requset.SignUpCertificationNumberSendRequest
import com.school_of_company.network.dto.auth.requset.SignUpRequest
import com.school_of_company.network.dto.reponse.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun signUp(body: SignUpRequest): Flow<Unit>

    fun login(body: LoginRequest): Flow<LoginResponse>

    fun tokenRefresh(refreshToken: String): Flow<LoginResponse>

    fun signLogout(): Flow<Unit>

    fun logout(): Flow<Unit>

    fun signUpCertificationNumberCertification(phoneNumber: String, code: String) : Flow<Unit>

    fun signUpCertificationNumberSend(body: SignUpCertificationNumberSendRequest) : Flow<Unit>
}