package com.school_of_company.network.mapper.auth.request

import com.school_of_company.model.auth.request.LoginRequestModel
import com.school_of_company.network.dto.auth.requset.LoginRequest

fun LoginRequestModel.toDto(): LoginRequest =
    LoginRequest(
        nickname = nickname,
        password = password
    )