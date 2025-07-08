package com.school_of_company.data.repository.member

import com.school_of_company.model.member.request.ModifyMemberInformationRequestModel
import com.school_of_company.model.member.response.GetAllMemberResponseModel
import com.school_of_company.model.member.response.GetMemberResponseModel
import kotlinx.coroutines.flow.Flow

interface MemberRepository {

    fun getMyProfileInformation() : Flow<GetMemberResponseModel>
    fun editMyProfileInformation(body: ModifyMemberInformationRequestModel) : Flow<Unit>
    fun getSpecificMemberProfileInformation(memberId: Long) : Flow<GetAllMemberResponseModel>
    fun getAllMemberProfileInformation() : Flow<List<GetAllMemberResponseModel>>
}