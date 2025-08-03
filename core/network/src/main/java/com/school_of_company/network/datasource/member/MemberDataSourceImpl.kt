package com.school_of_company.network.datasource.member

import com.school_of_company.network.api.MemberAPI
import com.school_of_company.network.dto.member.request.ModifyMemberInformationRequest
import com.school_of_company.network.dto.member.response.GetAllMemberResponse
import com.school_of_company.network.dto.member.response.GetMemberResponse
import com.school_of_company.network.util.performApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MemberDataSourceImpl @Inject constructor(
    private val memberApi: MemberAPI
) : MemberDataSource {
    override fun getMyProfileInformation(): Flow<GetMemberResponse> =
        performApiRequest { memberApi.getMyProfileInformation() }

    override fun editMyProfileInformation(body: ModifyMemberInformationRequest): Flow<Unit> =
        performApiRequest { memberApi.editMyProfileInformation(body = body) }

    override fun getSpecificMemberProfileInformation(memberId: Long): Flow<GetAllMemberResponse> =
        performApiRequest { memberApi.getSpecificMemberProfileInformation(memberId = memberId) }

    override fun getAllMemberProfileInformation(): Flow<List<GetAllMemberResponse>> =
        performApiRequest { memberApi.getAllMemberProfileInformation() }

    override fun withdrawalMember(): Flow<Unit> =
        performApiRequest { memberApi.withdrawalMember() }
}