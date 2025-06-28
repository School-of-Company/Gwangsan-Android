package com.school_of_company.network.datasource.main

import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.network.dto.auth.requset.LoginRequest
import com.school_of_company.network.dto.auth.requset.SignUpCertificationNumberSendRequest
import com.school_of_company.network.dto.auth.requset.SignUpRequest
import com.school_of_company.network.dto.main.reponse.MainListResponse
import com.school_of_company.network.dto.reponse.LoginResponse
import kotlinx.coroutines.flow.Flow

interface MainDataSource{
    fun allPostGet(
        type: Type,
        mode: Mode
    ): Flow<List<MainListResponse>>
}