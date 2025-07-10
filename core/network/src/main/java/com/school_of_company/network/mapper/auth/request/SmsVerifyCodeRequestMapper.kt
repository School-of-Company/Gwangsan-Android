package com.school_of_company.network.mapper.auth.request

import com.school_of_company.model.auth.request.SmsVerifyCodeRequestModel
import com.school_of_company.network.dto.auth.requset.SmsVerifyCodeRequest

fun SmsVerifyCodeRequestModel.toModel(): SmsVerifyCodeRequest =
    SmsVerifyCodeRequest(
        phoneNumber = this.phoneNumber,
        code = this.code
    )
