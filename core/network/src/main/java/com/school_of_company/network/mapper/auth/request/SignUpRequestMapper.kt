package com.school_of_company.network.mapper.auth.request

import com.school_of_company.model.auth.request.SignUpRequestModelBody
import com.kim.network.dto.auth.requset.SignUpRequest

fun SignUpRequestModelBody.toDto(): SignUpRequest =
    SignUpRequest(
        name = name,
        password = password,
        phoneNumber = phoneNumber,
        verificationCode = verificationCode,
        dong = dong,
        specialty = specialty,
        recommender = recommender,
        introduction = introduction,
        branch = branch
    )