package com.school_of_company.network.mapper.auth.response

import com.school_of_company.model.auth.response.LoginResponseModel
import com.school_of_company.network.dto.reponse.LoginResponse

fun LoginResponseModel.toDto(): LoginResponse =
    LoginResponse(
        refreshToken = refreshToken,
        refreshTokenExpiresIn = refreshTokenExpiresIn,
        accessToken = accessToken,
        accessTokenExpiresIn = accessTokenExpiresIn
    )