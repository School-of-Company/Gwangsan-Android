package com.school_of_company.network.api

import com.school_of_company.network.dto.reponse.LoginResponse
import com.school_of_company.network.dto.auth.requset.LoginRequest
import com.school_of_company.network.dto.auth.requset.SignUpCertificationNumberSendRequest
import com.school_of_company.network.dto.auth.requset.SignUpRequest
import com.school_of_company.network.dto.auth.requset.SmsVerifyCodeRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthAPI {
    @POST("/api/auth/signup")
    suspend fun signUp(
        @Body body: SignUpRequest
    )

    @POST("/api/auth/signin")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

    @PATCH("/api/auth/reissue")
    suspend fun tokenRefresh(): LoginResponse

    @DELETE("/api/auth/signout")
    suspend fun logout()

    @DELETE("/api/auth/out")
    suspend fun signLogout()

    @POST("/api/sms")
    suspend fun signUpCertificationNumberSend(
        @Body body: SignUpCertificationNumberSendRequest
    )

    @POST("/api/sms/verify")
    suspend fun signUpCertificationNumberCertification(
        @Body body: SmsVerifyCodeRequest
    )
}