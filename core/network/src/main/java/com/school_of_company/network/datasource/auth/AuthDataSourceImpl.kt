package com.school_of_company.network.datasource.auth

import com.school_of_company.network.api.AuthAPI
import com.school_of_company.network.datasource.auth.AuthDataSource
import com.school_of_company.network.dto.auth.requset.LoginRequest
import com.school_of_company.network.dto.auth.requset.SignUpRequest
import com.school_of_company.network.util.performApiRequest
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authAPI: AuthAPI
) : AuthDataSource {
    override suspend fun signUp(body: SignUpRequest) = performApiRequest { authAPI.signUp(body) }

    override suspend fun login(body: LoginRequest) = performApiRequest { authAPI.login(body) }

    override suspend fun tokenRefresh(refreshToken: String) = performApiRequest { authAPI.tokenRefresh(refreshToken) }

    override suspend fun sIgnLogout() = performApiRequest { authAPI.sIgnLogout() }

    override suspend fun logout() = performApiRequest { authAPI.logout() }
}
