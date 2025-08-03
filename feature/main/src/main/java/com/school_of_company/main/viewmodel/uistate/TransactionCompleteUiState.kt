package com.school_of_company.main.viewmodel.uistate

import androidx.compose.runtime.Stable

/**
 * 각 상태 구현체에 @Stable을 붙여서 Compose recomposition 최적화를 지원합니다.
 * Throwable 은 내부 상태가 너무 다양하고 변할 수 있으므로 안정하다고 보기 어렵기 때문에,
 * Compose가 안정성(stability)을 자동 판단하지 못할 수 있으므로 명시적으로 선언하였습니다.
 * */

@Stable
interface TransactionCompleteUiState {

    @Stable
    data object Loading : TransactionCompleteUiState

    @Stable
    data object Success : TransactionCompleteUiState

    @Stable
    data object Complete : TransactionCompleteUiState

    @Stable
    data class Error(val exception: Throwable) : TransactionCompleteUiState
}