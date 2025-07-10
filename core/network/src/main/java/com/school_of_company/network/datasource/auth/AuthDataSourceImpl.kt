package com.school_of_company.network.datasource.auth

import com.school_of_company.network.api.AuthAPI
import com.school_of_company.network.dto.auth.requset.LoginRequest
import com.school_of_company.network.dto.auth.requset.SignUpCertificationNumberSendRequest
import com.school_of_company.network.dto.auth.requset.SignUpRequest
import com.school_of_company.network.dto.auth.requset.SmsVerifyCodeRequest
import com.school_of_company.network.dto.reponse.LoginResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authAPI: AuthAPI
) : AuthDataSource {
    override fun signUp(body: SignUpRequest): Flow<Unit> =
        performApiRequest { authAPI.signUp(body) }

    override fun login(body: LoginRequest): Flow<LoginResponse> =
        performApiRequest { authAPI.login(body = body)}

    override fun tokenRefresh(refreshToken: String): Flow<LoginResponse> =
        performApiRequest { authAPI.tokenRefresh(refreshToken) }

    override fun signLogout(): Flow<Unit> =
        performApiRequest { authAPI.signLogout() }

    override fun logout(): Flow<Unit> =
        performApiRequest { authAPI.logout() }

    override fun signUpCertificationNumberCertification(body: SmsVerifyCodeRequest): Flow<Unit> =
        performApiRequest { authAPI.signUpCertificationNumberCertification(body = body) }

    override fun signUpCertificationNumberSend(body: SignUpCertificationNumberSendRequest): Flow<Unit> =
        performApiRequest { authAPI.signUpCertificationNumberSend(body = body) }
}
