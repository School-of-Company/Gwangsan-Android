package com.school_of_company.network.mapper.alert.response

import com.school_of_company.model.alert.response.GetAlertImagesModel
import com.school_of_company.model.alert.response.GetAlertResponseModel
import com.school_of_company.model.alert.response.GetReadAlertModel
import com.school_of_company.network.dto.alert.GetAlertImages
import com.school_of_company.network.dto.alert.GetAlertResponse
import com.school_of_company.network.dto.alert.GetReadAlert

fun GetReadAlert.toModel() = GetReadAlertModel(
    unread = this.unread
)

