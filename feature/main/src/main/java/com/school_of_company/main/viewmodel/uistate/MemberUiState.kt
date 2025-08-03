package com.school_of_company.main.viewmodel.uistate

import androidx.compose.runtime.Stable
import com.school_of_company.model.member.response.GetMemberResponseModel

/**
 * 각 상태 구현체에 @Stable을 붙여서 Compose recomposition 최적화를 지원합니다.
 * 특히 Success 내부의 data는 외부 모델(GetMemberResponseModel) 타입으로,
 * Compose가 안정성(stability)을 자동 판단하지 못할 수 있으므로 명시적으로 선언하였습니다.
 * */

@Stable
interface MemberUiState {

    @Stable
    data object Loading : MemberUiState

    @Stable
    data class Success(val data: GetMemberResponseModel) : MemberUiState

    @Stable
    data class Error(val exception: Throwable) : MemberUiState
}