package com.school_of_company.chat.viewmodel.uistate

import androidx.compose.runtime.Stable
import com.school_of_company.chat.ui.model.GetChatRoomResponseUi
import kotlinx.collections.immutable.PersistentList

@Stable
sealed interface DelteTradeReservationUiState {

    @Stable
    data object Loading : DelteTradeReservationUiState

    @Stable
    data object  BadRequest : DelteTradeReservationUiState

    @Stable
    data object Unauthorized : DelteTradeReservationUiState

    @Stable
    data object  NotFound : DelteTradeReservationUiState

    @Stable
    data object  Success : DelteTradeReservationUiState

    @Stable
    data class Error(val exception: Throwable) : DelteTradeReservationUiState
}