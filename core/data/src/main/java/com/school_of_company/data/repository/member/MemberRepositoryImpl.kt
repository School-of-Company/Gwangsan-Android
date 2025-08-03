package com.school_of_company.data.repository.member

import com.school_of_company.model.member.request.ModifyMemberInformationRequestModel
import com.school_of_company.model.member.response.GetAllMemberResponseModel
import com.school_of_company.model.member.response.GetMemberResponseModel
import com.school_of_company.network.datasource.member.MemberDataSource
import com.school_of_company.network.mapper.member.request.toDto
import com.school_of_company.network.mapper.member.response.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberDataSource: MemberDataSource
) : MemberRepository {
    override fun getMyProfileInformation(): Flow<GetMemberResponseModel> {
        return memberDataSource.getMyProfileInformation().map { it.toModel() }
    }

    override fun editMyProfileInformation(body: ModifyMemberInformationRequestModel): Flow<Unit> {
        return memberDataSource.editMyProfileInformation(body = body.toDto())
    }

    override fun getSpecificMemberProfileInformation(memberId: Long): Flow<GetAllMemberResponseModel> {
        return memberDataSource.getSpecificMemberProfileInformation(memberId = memberId).map { it.toModel() }
    }

    override fun getAllMemberProfileInformation(): Flow<List<GetAllMemberResponseModel>> {
        return memberDataSource.getAllMemberProfileInformation().map { it.map { list -> list.toModel() } }
    }

    override fun withdrawalMember(): Flow<Unit> {
        return memberDataSource.withdrawalMember()
    }
}