package com.school_of_company.network.mapper.auth.request

import com.school_of_company.model.auth.request.SignUpRequestModel
import com.school_of_company.network.dto.auth.requset.SignUpRequest

fun SignUpRequestModel.toDto(): SignUpRequest =
    SignUpRequest(
        name = this.name,
        nickname = this.nickname,
        password = this.password,
        phoneNumber = this.phoneNumber,
        dongName = this.dongName,
        placeName = this.placeName,
        recommender = this.recommender,
        specialties = this.specialties,
        description = this.description
    )