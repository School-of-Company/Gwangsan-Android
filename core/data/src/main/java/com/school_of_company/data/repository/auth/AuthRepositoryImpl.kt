package com.school_of_company.data.repository.auth

import com.school_of_company.datastore.datasource.AuthTokenDataSource
import com.school_of_company.model.auth.request.LoginRequestModel
import com.school_of_company.model.auth.request.SignUpCertificationNumberSendRequestModel
import com.school_of_company.model.auth.request.SignUpRequestModel
import com.school_of_company.model.auth.response.LoginResponseModel
import com.school_of_company.network.datasource.auth.AuthDataSource
import kotlinx.coroutines.flow.Flow
import com.school_of_company.network.mapper.auth.request.toDto
import com.school_of_company.network.mapper.auth.response.toModel
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDatasource: AuthDataSource,
    private val localDataSource: AuthTokenDataSource
) : AuthRepository {
    override fun signIn(body: LoginRequestModel): Flow<LoginResponseModel> {
        return remoteDatasource.login(
            body = body.toDto()
        ).transform { response ->
            emit(response.toModel())
        }
    }

    override fun signUp(body: SignUpRequestModel): Flow<Unit> {
        return remoteDatasource.signUp(body = body.toDto())
    }

    override fun logout(): Flow<Unit> {
        return remoteDatasource.logout()
    }

    override fun tokenRefresh(refreshToken: String): Flow<LoginResponseModel> {
        return remoteDatasource.tokenRefresh(refreshToken = refreshToken).transform { response ->
            emit(response.toModel())
        }
    }

    override fun signLogout(): Flow<Unit> {
        return remoteDatasource.signLogout()
    }

    override fun getRefreshToken(): Flow<String> {
        return localDataSource.getRefreshToken()
    }

    override suspend fun saveToken(token: LoginResponseModel) {
        localDataSource.setAccessToken(token.accessToken)
        localDataSource.setRefreshToken(token.refreshToken)
        localDataSource.setAccessTokenExp(token.accessTokenExpiresIn)
        localDataSource.setRefreshTokenExp(token.refreshTokenExpiresIn)
    }

    override suspend fun deleteTokenData() {
        localDataSource.removeAccessToken()
        localDataSource.removeRefreshToken()
        localDataSource.removeAccessTokenExp()
        localDataSource.removeRefreshTokenExp()
    }

    override fun signUpCertificationNumberSend(body: SignUpCertificationNumberSendRequestModel): Flow<Unit> {
        return remoteDatasource.signUpCertificationNumberSend(body = body.toDto())
    }

    override fun signUpCertificationNumberCertification(
        phoneNumber: String,
        code: String
    ): Flow<Unit> {
        return remoteDatasource.signUpCertificationNumberCertification(
            phoneNumber = phoneNumber,
            code = code
        )
    }
}