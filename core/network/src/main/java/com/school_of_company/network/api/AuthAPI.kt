package com.school_of_company.network.api

import com.school_of_company.network.dto.reponse.LoginResponse
import com.school_of_company.network.dto.auth.requset.LoginRequest
import com.school_of_company.network.dto.auth.requset.SignUpRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

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
    suspend fun tokenRefresh(
        @Header("refreshToken") refreshToken: String
    ): LoginResponse

    @DELETE("/api/auth/signout")
    suspend fun logout()

    @DELETE("/api/auth/out")
    suspend fun sIgnLogout()
}