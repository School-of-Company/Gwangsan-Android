package com.school_of_company.main.viewmodel.uistate

import androidx.compose.runtime.Stable
import com.school_of_company.main.ui.model.GetAlertResponseUi
import kotlinx.collections.immutable.PersistentList

/**
 * 각 상태 구현체에 @Stable을 명시하여 Compose recomposition 시 안정성 최적화를 보장합니다.
 * 특히, Success의 data는 외부 PersistentList 타입이므로, 실제로는 불변 객체이나
 * 컴파일러가 안정성(Stability)을 자동으로 판단하지 못할 수 있어 명시적으로 @Stable 처리하였습니다.
 */

@Stable
sealed interface GetAlertUiState {

    @Stable
    data object Loading : GetAlertUiState

    @Stable
    data object Empty : GetAlertUiState

    @Stable
    data class Success(val data : PersistentList<GetAlertResponseUi>) : GetAlertUiState

    @Stable
    data class Error(val exception: Throwable) : GetAlertUiState
}