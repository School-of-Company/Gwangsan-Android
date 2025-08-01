package com.school_of_company.network.api

import com.school_of_company.network.dto.member.request.ModifyMemberInformationRequest
import com.school_of_company.network.dto.member.response.GetAllMemberResponse
import com.school_of_company.network.dto.member.response.GetMemberResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MemberAPI {

    @GET("/api/member")
    suspend fun getMyProfileInformation() : GetMemberResponse

    @PATCH("/api/member")
    suspend fun editMyProfileInformation(
        @Body body: ModifyMemberInformationRequest
    )

    @GET("/api/member/{memberId}")
    suspend fun getSpecificMemberProfileInformation(
        @Path("memberId") memberId: Long
    ) : GetAllMemberResponse

    @GET("/api/member/all")
    suspend fun getAllMemberProfileInformation() : List<GetAllMemberResponse>

    @DELETE("/api/member")
    suspend fun withdrawalMember()
}