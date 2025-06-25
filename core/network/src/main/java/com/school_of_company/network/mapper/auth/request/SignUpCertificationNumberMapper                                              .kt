package com.school_of_company.network.mapper.auth.request

import com.school_of_company.model.auth.request.SignUpCertificationNumberSendRequestModel
import com.school_of_company.network.dto.auth.requset.SignUpCertificationNumberSendRequest

fun SignUpCertificationNumberSendRequestModel.toDto(): SignUpCertificationNumberSendRequest =
    SignUpCertificationNumberSendRequest(
        phoneNumber = phoneNumber
    )