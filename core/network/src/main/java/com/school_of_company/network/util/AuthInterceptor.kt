package com.school_of_company.network.util

import com.school_of_company.datastore.datasource.AuthTokenDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataSource: AuthTokenDataSource,
): Interceptor {

    private companion object {
        const val POST = "POST"
        const val PATCH = "PATCH"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val path = request.url.encodedPath
        val method = request.method

        val accessToken = runBlocking { dataSource.getAccessToken().first() }
        val refreshToken = runBlocking { dataSource.getRefreshToken().first() }

        val newRequest = when {
            // 회원가입, 로그인은 헤더 없이
            path.contains("/api/auth/") && method == POST -> {
                request
            }

            // 토큰 재발급은 refreshToken 사용
            path.endsWith("/api/auth/") && method == PATCH -> {
                request.newBuilder().addHeader("Authorization", "Bearer $refreshToken").build()
            }

            // 로그아웃, 회원탈퇴 등 그 외는 accessToken 사용
            else -> {
                request.newBuilder().addHeader("Authorization", "Bearer $accessToken").build()
            }
        }

        val response = chain.proceed(newRequest)

        return when (response.code) {
            204, 205 -> response.newBuilder().code(200).build()
            else -> response
        }
    }
}