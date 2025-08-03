package com.school_of_company.network.datasource.member

import com.school_of_company.network.dto.member.request.ModifyMemberInformationRequest
import com.school_of_company.network.dto.member.response.GetAllMemberResponse
import com.school_of_company.network.dto.member.response.GetMemberResponse
import kotlinx.coroutines.flow.Flow

interface MemberDataSource {

    fun getMyProfileInformation() : Flow<GetMemberResponse>
    fun editMyProfileInformation(body: ModifyMemberInformationRequest) : Flow<Unit>
    fun getSpecificMemberProfileInformation(memberId: Long) : Flow<GetAllMemberResponse>
    fun getAllMemberProfileInformation() : Flow<List<GetAllMemberResponse>>
    fun withdrawalMember() : Flow<Unit>
}