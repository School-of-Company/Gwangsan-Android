package com.school_of_company.network.mapper.auth.request

import com.school_of_company.model.auth.request.SignUpRequestModel
import com.school_of_company.network.dto.auth.requset.SignUpRequest

fun SignUpRequestModel.toDto(): SignUpRequest =
    SignUpRequest(
        name = name,
        password = password,
        phoneNumber = phoneNumber,
        verificationCode = verificationCode,
        dong = dong,
        specialty = specialty,
        recommender = recommender,
        introduction = introduction,
        branch = branch,
        nickname = nickname
    )