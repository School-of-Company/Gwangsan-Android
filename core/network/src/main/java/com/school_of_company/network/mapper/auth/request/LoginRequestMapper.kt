package com.school_of_company.network.mapper.auth.request

import com.school_of_company.model.auth.request.LoginUpRequestModelBody
import com.kim.network.dto.auth.requset.LoginRequest

fun LoginUpRequestModelBody.toDto(): LoginRequest =
    LoginRequest(
        nickname = nickname,
        password = password
    )