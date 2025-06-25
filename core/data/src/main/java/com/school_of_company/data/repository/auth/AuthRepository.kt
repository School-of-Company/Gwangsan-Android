package com.school_of_company.data.repository.auth

import com.school_of_company.model.auth.request.LoginRequestModel
import com.school_of_company.model.auth.request.SignUpCertificationNumberSendRequestModel
import com.school_of_company.model.auth.request.SignUpRequestModel
import com.school_of_company.model.auth.response.LoginResponseModel
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

    fun signUpCertificationNumberCertification(phoneNumber: String, code: String) : Flow<Unit>

    fun signUpCertificationNumberSend(body: SignUpCertificationNumberSendRequestModel) : Flow<Unit>
}